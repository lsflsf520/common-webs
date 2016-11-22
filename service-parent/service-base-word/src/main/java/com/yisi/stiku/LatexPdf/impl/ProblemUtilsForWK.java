package com.yisi.stiku.LatexPdf.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yisi.stiku.LatexPdf.Vo.ImgaeAttr;
import com.yisi.stiku.LatexPdf.impl.LatexToImageUtils.ImageVO;
import com.yisi.stiku.LatexPdf.utils.LatexPdfBaseConstant;
import com.yisi.stiku.basedata.entity.TbProblemContent;
/*import com.yisi.stiku.bd.problem.entity.TbProblem;
 import com.yisi.stiku.bd.problem.entity.TbProblemContent;*/
import com.yisi.stiku.common.utils.Encoder;

/**
 * 题目内容处理工具类
 * 
 * @author Xiang
 *
 */
@Component
public class ProblemUtilsForWK {

	private static Logger logger = LoggerFactory.getLogger(ProblemUtilsForWK.class);
	private static ExecutorService makeImageVOExecutor = Executors
			.newFixedThreadPool(LatexPdfBaseConstant.SAME_TIME_LATEX_FORMULA);

	private class ImageVoTask implements Callable<ImageVO> {

		private final String mathstr;
		private final String tag;
		private final int i;
		private final LatexToImageUtils liUtils;
		private long problemId;
		private ImgaeAttr imageAttr;

		public ImageVoTask(String mathstr, String tag, int i, LatexToImageUtils liUtils, long problemId,ImgaeAttr imageAttr) {

			super();
			this.mathstr = mathstr;
			this.tag = tag;
			this.i = i;
			this.liUtils = liUtils;
			this.problemId = problemId;
			this.imageAttr = imageAttr;
		}

		@Override
		public ImageVO call() throws Exception {

			return liUtils.convertLatex2Image(mathstr, "img_" + i + "_" + imageAttr.getTimeStamp() + ".png", tag, problemId,imageAttr);
		}
	}

	/**
	 * 将一条题目内容转换成图片并返回内容html字符串签
	 * 
	 * @param content
	 *            题目内容：题干或答案
	 * @param tag
	 *            题目标签，多数情况为题目id,用于分类
	 * @return 图片信息
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public String convertProblemContent2ImageTag(String content, String tag, long problemId,ImgaeAttr imageAttr)
			throws InterruptedException, ExecutionException {

		// log.debug("准备将["+tag+"]内容["+content+"]转换成图片并返回内容html字符串签......");
		// 找到以$开头且$结尾的公式
		Pattern pattern = Pattern.compile("\\${1}[^\\$]+\\${1}");
		Matcher matcher = pattern.matcher(content);
		int i = 0;
		StringBuffer sbr = new StringBuffer();
		LatexToImageUtils liUtils = new LatexToImageUtils();
		Map<String, Future<ImageVO>> imageVoMap = new HashMap<String, Future<ImageVO>>();
		// 遍历查询结构，寻找需要替换的部分
		while (matcher.find()) {
			String mathstr = matcher.group();
			/* html解码，将&lg;转换成> */
			ImageVoTask imageVoTask = new ImageVoTask(Encoder.unescapeHtml(mathstr), tag, i, liUtils,  problemId,imageAttr);
			Future<ImageVO> pendingProblem = makeImageVOExecutor.submit(imageVoTask);
			/* 有相同的数学符号，不再重复计算 */
			if (null == imageVoMap.get(mathstr))
				imageVoMap.put(mathstr, pendingProblem);
			i++;
		}

		// 再次遍历查询结构，替换为实际内容
		matcher.reset();
		i = 0;
		while (matcher.find()) {
			String mathstr = matcher.group();
			ImageVO imageVO = imageVoMap.get(mathstr).get();
			if (imageVO != null) {
				// 将图片缩小n倍以适配行高
				float ratio = imageAttr.getRetio_px();
				String imgTag = "<img class='latex' src='" + imageVO.getFilepath() + "' "
						// + "style='vertical-align: middle;margin-bottom:8px' "
						+ "width='" + imageVO.getWidth() * ratio + "px' "
						+ "height='" + imageVO.getHeight() * ratio + "px' "
						+ ">";
				matcher.appendReplacement(sbr, imgTag);
			} else {
				// TODO 放入默认图片
				// String imgTag
				// ="<img class='latex' width='5px' height='5px' alt='图片加载失败'>";
				String imgTag = "[符号错误]";
				matcher.appendReplacement(sbr, imgTag);
			}
			i++;
		}
		matcher.appendTail(sbr);
		return sbr.toString();
	}

	/**
	 * 为wkhtmltopdf转换提供单个题目的html内容 (样式与js必须与wkhtmltopdf的CreateMagazine搭配使用)
	 * 
	 * @param problemContent
	 *            题目内容
	 * @return
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public String convertProblemContent2DivTag(TbProblemContent problemContent, ImgaeAttr imageAttr)
			throws InterruptedException, ExecutionException {

		StringBuffer content = new StringBuffer();
		content.append("<div class='problem'>");
		content.append("<div class='problem-content'>");
		// 将题目题干转换成标签
		String problemStr = this.convertProblemContent2ImageTag(problemContent.getProblemContent(), problemContent.getId()
				+ "", problemContent.getId(),imageAttr);
		content.append(problemStr);
		// class=problem-content
		content.append("</div>");
		// 若是选择题，则将选项转换成标签
		if (0 == problemContent.getType()) {
			// 选项框
			content.append("<ul id='" + problemContent.getId() + "' class='problem-options'>");
			// A选项
			content.append("<li class='problem-option'>");
			content.append("<div><strong>A.</strong>");
			String optionA = this.convertProblemContent2ImageTag(problemContent.getaContent(),
					problemContent.getId() + "_a", problemContent.getId(),imageAttr);
			content.append(optionA);
			// div-a
			content.append("</div>");
			content.append("</li>");
			// B选项
			content.append("<li class='problem-option'>");
			content.append("<div><strong>B.</strong>");
			String optionB = this.convertProblemContent2ImageTag(problemContent.getbContent(),
					problemContent.getId() + "_b", problemContent.getId(),imageAttr);
			content.append(optionB);
			// div-b
			content.append("</div>");
			content.append("</li>");
			// C选项
			content.append("<li class='problem-option'>");
			content.append("<div><strong>C.</strong>");
			String optionC = this.convertProblemContent2ImageTag(problemContent.getcContent(),
					problemContent.getId() + "_c", problemContent.getId(),imageAttr);
			content.append(optionC);
			// div-c
			content.append("</div>");
			content.append("</li>");
			// D选项
			content.append("<li class='problem-option'>");
			content.append("<div><strong>D.</strong>");
			String optionD = this.convertProblemContent2ImageTag(problemContent.getdContent(),
					problemContent.getId() + "_d", problemContent.getId(),imageAttr);
			content.append(optionD);
			// div-d
			content.append("</div>");
			content.append("</li>");

			content.append("</ul>");
		}

		// class=problem
		content.append("</div>");
		return content.toString();
	}

	public String convertProblemAnswer2DivTag(TbProblemContent problemContent, ImgaeAttr imageAttr)
			throws InterruptedException, ExecutionException {

		StringBuffer answer = new StringBuffer();
		answer.append("<div class='problem-answer'>");
		String answerStr = this.convertProblemContent2ImageTag(problemContent.getAnswerContent(), problemContent.getId()
				+ "_answer", problemContent.getId(),imageAttr);
		answer.append(answerStr);
		answer.append("</div>");
		return answer.toString();
	}

	/**
	 * 
	 * Creates a PDF file: hello_narrow.pdf
	 * 
	 * @param args
	 *            no arguments needed
	 */
	public static void main(String[] args) {

		// 获取系统中可用的字体的名字
		/*
		 * GraphicsEnvironment e =
		 * GraphicsEnvironment.getLocalGraphicsEnvironment(); String[] fontName
		 * = e.getAvailableFontFamilyNames(); for(int i = 0; i<fontName.length ;
		 * i++) { System.out.println(fontName[i]); }
		 */
		ProblemUtilsForWK utils = new ProblemUtilsForWK();
		String testProblem = "已知椭圆中心在原点，一个焦点为($\\sqrt 3$，$0$)，且长轴长是短轴长的$2$倍，则该椭圆的标准方程是__________.";
		String str;
		try {
			ImgaeAttr imageAttr = new ImgaeAttr();
			str = utils.convertProblemContent2ImageTag(testProblem, "12453_answer",  12453,imageAttr);
			System.out.println(str);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//
		// String a=utils.convertProblemContent2ImageTag(a1, "test_a");
		// System.out.println(a);
		// String b=utils.convertProblemContent2ImageTag(a2, "test_b");
		// System.out.println(b);
		//
		// String
		// c=utils.convertProblemContent2ImageTag(Encoder.unescapeHtml(a3),
		// "test_c");
		// System.out.println(c);
		// String d=utils.convertProblemContent2ImageTag(a4, "test_d");
		// System.out.println(d);
		// String ans = utils.convertProblemContent2ImageTag(answer1,
		// "test_ans");
		// System.out.println(ans);
	}
}
