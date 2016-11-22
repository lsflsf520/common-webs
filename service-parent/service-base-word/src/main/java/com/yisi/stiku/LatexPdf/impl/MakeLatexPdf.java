package com.yisi.stiku.LatexPdf.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yisi.stiku.LatexPdf.Vo.ImgaeAttr;
import com.yisi.stiku.LatexPdf.Vo.MakePdfResultVo;
import com.yisi.stiku.LatexPdf.Vo.ProblemShaVo;
import com.yisi.stiku.LatexPdf.Vo.QueryProblemContentVo;
import com.yisi.stiku.LatexPdf.service.IMakeLatexPdf;
import com.yisi.stiku.LatexPdf.serviceUtils.LatexPublicConstants;
import com.yisi.stiku.LatexPdf.utils.LatexPdfBaseConstant;
import com.yisi.stiku.LatexPdf.utils.ProcessProblemLatex;
import com.yisi.stiku.basedata.entity.TbProblemContent;
import com.yisi.stiku.basedata.rpc.service.ProblemContentRpcService;
import com.yisi.stiku.common.exception.BaseRuntimeException;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

@Service
public class MakeLatexPdf implements IMakeLatexPdf {

	private Logger log = LoggerFactory.getLogger(MakeLatexPdf.class);
	private final static String system = System.getProperty("os.name");

	@Autowired
	private ProblemContentRpcService problemContentRpcService;
	/*
	 * @Autowired private ProblemUtilsForWK problemUtilsForWK;
	 */
	@Autowired
	private ProcessProblemLatex processProblemLatex;

	private StringBuffer replaceCommon(String splitStr,String src,HashMap<Long, ProblemShaVo> problemContentMap,
			Map<Long, Future<ProblemShaVo>> futureProblemMap) throws InterruptedException, ExecutionException{
		StringBuffer sbr = new StringBuffer("");
		Pattern pattern = Pattern.compile(splitStr + "{1}"
				+ "[^" + splitStr + "]"
				+ "+" + splitStr + "{1}");
		Matcher matcher = pattern.matcher(src);
		//log.debug("需要处理的文本:"+src);
		while (matcher.find()) {
			String problemStr = matcher.group();
			log.debug("发现混排中需要处理的部分["+problemStr+"]。");
			long problemId = Long.valueOf(problemStr.substring(
					problemStr.indexOf(splitStr) + splitStr.length(),
					problemStr.lastIndexOf(splitStr)));
			ProblemShaVo problemShaVo = problemContentMap.get(problemId);
			if (null == problemShaVo) {
				Future<ProblemShaVo> futureProblemShaVo = futureProblemMap.get(problemId);
				if (null != futureProblemShaVo) {
					problemShaVo = futureProblemShaVo.get();
				} else {
					log.error("无法找到对应题目[" + problemId + "]的替换内容！中止文档生成。");
					throw new BaseRuntimeException("Problem has Error", "无法找到对应题目[" + problemId + "]的替换内容！");
				}
			}
			String problemSelf = null;
			if (null != problemShaVo) {
				problemContentMap.put(problemId, problemShaVo);
				if (LatexPublicConstants.Split_Symbol_ProblemStem.equals(splitStr)){
					problemSelf = problemShaVo.getStemHtml();
				}else if(LatexPublicConstants.Split_Symbol_Answer.equals(splitStr)){
					problemSelf = problemShaVo.getAnswerHtml();
				}else{
					problemSelf = "";
				}
			} else {
				log.error("没有题目[" + problemId + "]HTML内容！中止文档生成。");
				throw new BaseRuntimeException("Problem has Error", "没有题目[" + problemId + "]HTML化内容！");
			}
			try {
				matcher.appendReplacement(sbr, problemSelf.replaceAll("\\$\\$", ""));
			} catch (Exception e) {
				log.error("出错题目id：[" + problemId + "]，内容：[" + problemSelf + "]", e);
				throw new BaseRuntimeException("Problem has Error", "题目id[" + problemId + "]有错:" + e.getMessage());
			}
		}
		matcher.appendTail(sbr);
		return sbr;
	}
	
	/**
	 * 将源map转换为可以实际写入html用以生成pdf的实际map
	 * 
	 * @param src
	 *            源map
	 * @param target
	 *            实际map
	 * @param problemContentMap
	 *            包含已将题目中的LaTeX转换为图片的map
	 * @param type
	 *            1：题目本身；2：题目答案
	 * @return 成功返回success
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public String convertMap(Map<String, String> src, Map<String, String> target,
			HashMap<Long, ProblemShaVo> problemContentMap, int type,
			Map<Long, Future<ProblemShaVo>> futureProblemMap, String fileName) throws InterruptedException,
			ExecutionException {

		/* 没有题目需要替换，直接将源map中内容写入实际map */
		if ((null == problemContentMap || problemContentMap.size() == 0)
				&& (null == futureProblemMap || futureProblemMap.size() == 0)) {
			for (Map.Entry<String, String> entry : src.entrySet()) {
				target.put(entry.getKey().toString(), entry.getValue().toString());
			}
		} else {
			for (Map.Entry<String, String> entry : src.entrySet()) {
				String contentKey = entry.getKey().toString();
				String contentValue = entry.getValue().toString();
				Pattern pattern = Pattern.compile(LatexPublicConstants.Split_Symbol + "{1}"
						+ "[^" + LatexPublicConstants.Split_Symbol + "]"
						+ "+" + LatexPublicConstants.Split_Symbol + "{1}");
				Matcher matcher = pattern.matcher(contentValue);
				StringBuffer sbr = new StringBuffer("");
				while (matcher.find()) {
					String problemStr = matcher.group();
					long problemId = Long.valueOf(problemStr.substring(
							problemStr.indexOf(LatexPublicConstants.Split_Symbol) + 1,
							problemStr.lastIndexOf(LatexPublicConstants.Split_Symbol)));
					ProblemShaVo problemShaVo = problemContentMap.get(problemId);
					if (null == problemShaVo) {
						Future<ProblemShaVo> futureProblemShaVo = futureProblemMap.get(problemId);
						if (null != futureProblemShaVo) {
							problemShaVo = futureProblemShaVo.get();
						} else {
							log.error("无法找到对应题目[" + problemId + "]的替换内容！中止文档生成。");
							return "无法找到对应题目[" + problemId + "]的替换内容！";
						}
					}
					String problemSelf = null;
					if (null != problemShaVo) {
						problemContentMap.put(problemId, problemShaVo);
						problemSelf = type == ProcessProblemLatex.Problem_Stem ?
								problemShaVo.getStemHtml()
								: problemShaVo.getAnswerHtml();
					} else {
						log.error("没有题目[" + problemId + "]HTML内容！中止文档生成。");
						return "没有题目[" + problemId + "]HTML化内容！";
					}
					try {
						matcher.appendReplacement(sbr, problemSelf.replaceAll("\\$\\$", ""));
					} catch (Exception e) {
						log.error("出错题目id：[" + problemId + "]，内容：[" + problemSelf + "]", e);
						throw new BaseRuntimeException("Problem has Error", "题目id[" + problemId + "]有错:" + e.getMessage());
					}
					/*
					 * log.warn(problemStr+"无法找到对应题目["+problemId+"]LaTeX化内容！中止文档生成。"
					 * ); return problemStr+"无法找到对应题目["+problemId+"]LaTeX化内容！";
					 */
				}
				matcher.appendTail(sbr);
				log.debug("处理["+contentKey+"]混排题目.........");
				StringBuffer sbrProblemStem = replaceCommon(LatexPublicConstants.Split_Symbol_ProblemStem,sbr.toString(),problemContentMap,futureProblemMap);
				log.debug("处理["+contentKey+"]混排答案.........");
				StringBuffer sbrProblemAnswer = replaceCommon(LatexPublicConstants.Split_Symbol_Answer,sbrProblemStem.toString(),problemContentMap,futureProblemMap);
				target.put(contentKey, sbrProblemAnswer.toString());
			}
		}
		return LatexPdfBaseConstant.Success;
	}

	@Override
	public MakePdfResultVo makeNormalLatexPdf(String header, String footer, String workDir,
			String fileName, String templetFileName, Map<String, String> problems,
			Map<String, String> answers, Set<Long> problemSet, boolean showPageNum, String customCommand) throws Exception {

		MakePdfResultVo result = new MakePdfResultVo(LatexPdfBaseConstant.Failure);

		/* 准备填充html */
		long start = System.currentTimeMillis();
		log.info("-------------------------开始准备写入文件中的实际内容:" + fileName);
		List<Long> problemIdList = null;
		HashMap<Long, ProblemShaVo> problemContentMap = null;
		Map<Long, Future<ProblemShaVo>> futureProblemMap = new HashMap<Long, Future<ProblemShaVo>>();
		if (null != problemSet && problemSet.size() > 0) {
			problemIdList = new ArrayList<Long>(problemSet);
			log.info("开始从problemContentRpcService获取题目内容[" + problemIdList + "]......");
			problemContentMap = new HashMap<Long, ProblemShaVo>();
			List<TbProblemContent> problemContentList = problemContentRpcService
					.getProblemContentsByProblemIds(problemIdList);
			log.info("从problemContentRpcService获取题目内容完成");
			if (null == problemContentList || problemContentList.size() == 0) {
				log.warn("没有任何LaTeX化的题目！中止文档生成。");
				return result.fillFailureReason("没有任何LaTeX化的题目！");
			}
			if (problemContentList.size() < problemIdList.size()) {
				List<Long> problemContentIdList = new ArrayList<Long>();
				for (TbProblemContent problem : problemContentList) {
					problemContentIdList.add(problem.getId());
				}
				problemIdList.removeAll(problemContentIdList);
				log.warn("有题目" + problemIdList + "没有完成LaTeX化！中止文档生成。");
				return result.fillFailureReason("有题目" + problemIdList + "没有完成LaTeX化！");
			}
			/*
			 * 简单顺序查找，以后考虑优化性能
			 */
			long sortStart = System.currentTimeMillis();
			List<TbProblemContent> problemContentOrderList = new ArrayList<TbProblemContent>();
			for (Long id : problemIdList) {
				int i = -1;
				for (TbProblemContent content : problemContentList) {
					i++;
					if (content.getId().longValue() == id.longValue()) {
						problemContentOrderList.add(content);

						break;
					}
				}
				problemContentList.remove(i);
			}
			log.debug("题目按传入顺序排序完成，费时:" + (System.currentTimeMillis() - sortStart) + "ms");
			ImgaeAttr imageAttr = new ImgaeAttr();
			for (TbProblemContent problemContent : problemContentOrderList) {
				long problemId = problemContent.getId();
				// log.debug("获取、检查题目["+problemId+"]的html串.......");
				QueryProblemContentVo queryProblemContent = null;
				try {
					queryProblemContent = processProblemLatex.checkAndGetProblem(problemContent, fileName, imageAttr);
					if (null == queryProblemContent.getProblemShaVo()) {
						futureProblemMap.put(problemId, queryProblemContent.getFutureProblemShaVo());
					} else {
						problemContentMap.put(problemId, queryProblemContent.getProblemShaVo());
					}
				} catch (Exception e) {
					log.error("[" + problemId + "]题干LaTeX转pdf异常：", e);
					return result.fillFailureReason("[" + problemId + "]题干LaTeX转pdf异常！" + e);
					// throw e;
				}

				// log.info(".......完成获取、检查题目["+problemContent.getId()+"]的HTML串");
			}

		}
		log.info("............[" + fileName + "]完成获取、检查题目HTML串，费时:" + (System.currentTimeMillis() - start)
				+ "ms.............");
		Map<String, String> processedMap = new HashMap<String, String>();
		String tempResult = convertMap(problems, processedMap, problemContentMap, ProcessProblemLatex.Problem_Stem,
				futureProblemMap, fileName);
		if (!LatexPdfBaseConstant.Success.equals(tempResult)) {
			return result.fillFailureReason(tempResult);
		}

		tempResult = convertMap(answers, processedMap, problemContentMap, ProcessProblemLatex.Problem_Answer,
				futureProblemMap, fileName);
		if (!LatexPdfBaseConstant.Success.equals(tempResult)) {
			return result.fillFailureReason(tempResult);
		}

		log.info("完成写入文件" + fileName + "中的实际全部内容，共费时:" + (System.currentTimeMillis() - start) + "ms");

		String htmlName = fileName + ".html";
		String pdfName = fileName + LatexPdfBaseConstant.PDF_EXTENSION;
		File workDirPath = new File(workDir);
		if (!workDirPath.exists())
			workDirPath.mkdirs();
		String factHtmlNameFullPath = workDir + htmlName;
		log.info("开始生成实际html文件:" + factHtmlNameFullPath);
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(LatexPdfBaseConstant.PDF_TEMPLET_FULLPATH));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setDefaultEncoding("UTF-8");
		Template temp = cfg.getTemplate(templetFileName, "UTF-8");
		Writer out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(factHtmlNameFullPath), "UTF-8");
			temp.process(processedMap, out);
			out.flush();
		} finally {
			if (null != out)
				out.close();
		}

		/* 将pdf文件名变为全路径 */
		pdfName = workDir + pdfName;
		/* 准备生成pdf */
		start = System.currentTimeMillis();
		log.info("开始生成pdf文档:" + pdfName);
		String command = " --footer-font-size 7 --footer-spacing 5 --footer-font-name KaiTi";
		if (null != header && !"".equals(header)) {
			command = command + " --header-left " + header;
		}
		if (null != footer && !"".equals(footer)) {
			command = command + " --footer-left " + footer;
		}
		command = command + " --footer-center 创数教育www.17daxue.com ";
		if (showPageNum) {
			command = command + " --footer-right [page]/[topage] ";
		}
		if (null != customCommand && !"".equals(customCommand)) {
			command = command + " " + customCommand + " ";
		}
		command = command + factHtmlNameFullPath + " " + pdfName;
		if (system.toLowerCase().contains("windows")) // windows系统
			command = "D:/temp/wkhtmltopdf/bin/wkhtmltopdf.exe " + command;
		else if (system.toLowerCase().contains("linux")) // linux 系统
			command = "wkhtmltopdf " + command + "";
		if (!"".equals(command)) {
			result = execCmd(command, fileName, pdfName);
			if (result.getPageNums() == 0) {
				log.warn("重试！！执行外部命令生成pdf文档:" + pdfName + "未能正常完成。");
				result = execCmd(command, fileName, pdfName);
			}
		}
		return result;
	}

	private MakePdfResultVo execCmd(String command, String fileName, String pdfName) {

		MakePdfResultVo result = new MakePdfResultVo(LatexPdfBaseConstant.Failure);
		try {
			long start = System.currentTimeMillis();
			log.debug("准备执行外部命令：" + command);
			Process p = Runtime.getRuntime().exec(command);
			InputStream stderr = p.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			InputStream stdOutput = p.getErrorStream();
			InputStreamReader isrOutPut = new InputStreamReader(stdOutput);
			BufferedReader brOutPut = new BufferedReader(isrOutPut);
			String lineOutPut = null;
			// log.debug("<ERROR>");
			String pageNumLine = null;
			while ((line = br.readLine()) != null) {
				if (line.contains("] Page")) {
					log.debug(fileName + LatexPdfBaseConstant.PDF_EXTENSION + ":" + line);
					if (line.contains("==] Page")) {
						pageNumLine = line;
					}
				}
				if (line.contains("Done")) {
					log.debug(fileName + LatexPdfBaseConstant.PDF_EXTENSION + ":" + line);
				}
			}
			// log.debug("</ERROR>");
			// log.debug("<OUTPUT>");
			while ((lineOutPut = brOutPut.readLine()) != null) {
				log.debug(lineOutPut);
			}
			// log.debug("</OUTPUT>");
			p.waitFor();
			if (pageNumLine != null) {
				result.setPageNums(Integer.valueOf(pageNumLine.substring(pageNumLine.lastIndexOf(" ") + 1)));
				result.setPdfName(pdfName);
			} else {
				result.fillFailureReason("发生未知错误！");
			}
			log.info("执行外部命令wkhtmltopdf生成pdf文档:" + pdfName + " 费时："
					+ (System.currentTimeMillis() - start) + "ms");
		} catch (Exception e) {
			log.error("执行外部命令wkhtmltopdf生成pdf文档[" + pdfName + "]错误：", e);
		}
		return result;
	}

}
