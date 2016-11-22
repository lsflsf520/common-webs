package com.yisi.stiku.LatexPdf.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.Weighers;
import com.yisi.stiku.LatexPdf.Vo.ImageBaseAttr;
import com.yisi.stiku.LatexPdf.Vo.ImgaeAttr;
import com.yisi.stiku.LatexPdf.Vo.ProblemShaVo;
import com.yisi.stiku.LatexPdf.Vo.QueryProblemContentVo;
import com.yisi.stiku.LatexPdf.impl.ProblemUtilsForWK;
import com.yisi.stiku.basedata.entity.TbProblemContent;
import com.yisi.stiku.common.utils.EncryptTools;
import com.yisi.stiku.utils.OffLineDocBaseConstant;

@Service
public class ProcessProblemLatex {
	
	public static final String SaveFileName = LatexPdfBaseConstant.PDF_TEMPLET_FULLPATH+"ProblemSha.data";
	public static final int Problem_All = 0;
	public static final int Problem_Stem = 1;
	public static final int Problem_Answer = 2;
	public static final String Pending_Hint ="future";
	
	private static Logger log = LoggerFactory.getLogger(ProcessProblemLatex.class);
	/**题目html串的本地缓存,LRU策略，经统计，一个题目本身的内容平均大小为733.1497个字节， */
	private static ConcurrentLinkedHashMap<Long, ProblemShaVo> htmlProblemMap =
			new ConcurrentLinkedHashMap.Builder<Long, ProblemShaVo>()
			.maximumWeightedCapacity(5000).weigher(Weighers.singleton()).build();
	/**题目生成时的控制缓存，防止多个线程同时生成一个题目的内容 */	
	private static ConcurrentHashMap<String, Future<ProblemShaVo>> pendingProblemMap 
		= new ConcurrentHashMap<String, Future<ProblemShaVo>>();
	
	private static ExecutorService makeProblemExecutor = Executors.newFixedThreadPool(LatexPdfBaseConstant.SAME_TIME_PROBLEMS);
	@Autowired
	private ProblemUtilsForWK problemUtilsForWK;
	
	@PreDestroy
	public void initWork(){
		makeProblemExecutor.shutdown();
	}
	
	public static void removePendingProblem(Long problemId,ImageBaseAttr imageBaseAttr){
		pendingProblemMap.remove(makePendingProblemMapKey(imageBaseAttr,problemId));
	}
	
	private static String makePendingProblemMapKey(ImageBaseAttr imageBaseAttr,Long problemId){
		return imageBaseAttr.toString()+problemId;
	}
	
	public static final String STEM_KEY ="Stem";
	public static final String STEM_SHA_KEY =STEM_KEY+OffLineDocBaseConstant.SHA_POSTFIX;
	public static final String ANSWER_SHA_EKY =OffLineDocBaseConstant.ANSWER_POSTFIX+OffLineDocBaseConstant.SHA_POSTFIX;
	
	/**
	 * 保存题目的sha串，题干的html串，答案html串到文件
	 * @param problemId 题目id
	 * @param problemStemSha 题干的sha串
	 * @param answerSha 答案的sha串
	 * @param problemContent 题干的html串
	 * @param answerContent 答案的html串
	 */
	public void saveProblemDetail(ProblemShaVo problemShaVo,ImgaeAttr imageAttr){
		String problemDetailFile = problemShaVo.getProblemId()+OffLineDocBaseConstant.PROBLEM_DETAIL_EXTENSION;
		String problemSaveDir = OffLineDocBaseConstant.PROBLEM_DETAIL_FULLPATH 
				+ (problemShaVo.getProblemId() % OffLineDocBaseConstant.DIR_COUNTS)+"/"+imageAttr.getSaveDivisionDir();
		UtilOperate.checkDirAndCreate(problemSaveDir);
		//log.debug("["+problemShaVo.getProblemId()+"]准备将题目保存进详情文件["+problemSaveDir+problemDetailFile+"].......");
        File file =new File(problemSaveDir+problemDetailFile);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream objOut=new ObjectOutputStream(out);
            objOut.writeObject(problemShaVo);
            objOut.flush();
            objOut.close();
            log.info("["+problemShaVo.getProblemId()+"]题目的详情保存到文件["+problemSaveDir+problemDetailFile+"]成功。");
        } catch (IOException e) {
        	log.error("["+problemShaVo.getProblemId()+"]IO错误，无法保存题目的详情",e);
        }		
	}

	/**
	 * 从题目的详情文件中读取题目，并刷新题目html串的本地缓存
	 * @param problemId
	 * @return
	 */
	public ProblemShaVo loadProblemDetail(long problemId,ImgaeAttr imageAttr){
		String problemDetailFile = problemId+OffLineDocBaseConstant.PROBLEM_DETAIL_EXTENSION;
		String problemSaveDir = OffLineDocBaseConstant.PROBLEM_DETAIL_FULLPATH 
				+ (problemId % OffLineDocBaseConstant.DIR_COUNTS)+"/"+imageAttr.getSaveDivisionDir();
		try {
			ProblemShaVo problemShaVoInFile=null;
	        File file =new File(problemSaveDir+problemDetailFile);
            ObjectInputStream objIn=new ObjectInputStream(new FileInputStream(file));
            problemShaVoInFile=(ProblemShaVo)objIn.readObject();
            objIn.close();
            
			String stemSha = problemShaVoInFile.getStemSha();
			String answerSha = problemShaVoInFile.getAnswerSha();
			String stemOrigin = problemShaVoInFile.getStemOrigin();
			String answerOrigin = problemShaVoInFile.getAnswerOrigin();
			String htmlSha = problemShaVoInFile.getHtmlSha();
			String stemHtml = problemShaVoInFile.getStemHtml();
			String answerHtml = problemShaVoInFile.getAnswerHtml();
			
			/*校验文件内容的齐备性*/
			if (null==stemSha||"".equals(stemSha)
					||null==answerSha||"".equals(answerSha)
					||null==stemOrigin||"".equals(stemOrigin)
					||null==answerOrigin||"".equals(answerOrigin)
					||null==htmlSha||"".equals(htmlSha)
					||null==stemHtml||"".equals(stemHtml)
					||null==answerHtml||"".equals(answerHtml)){
				log.warn("["+problemId+"]题目详情文件中内容不全！再次尝试从缓存中读取.");
				return imageAttr.isBaseImage()? htmlProblemMap.get(problemId):null;
			}
			/*校验文件内容的正确性*/
			if (!EncryptTools.EncryptBySHA1(stemOrigin).equals(stemSha)
					||!EncryptTools.EncryptBySHA1(answerOrigin).equals(answerSha)
					||!EncryptTools.EncryptBySHA1(stemHtml+answerHtml).equals(htmlSha)){
				log.warn("["+problemId+"]题目详情文件中内容无法通过正确性校验！再次尝试从缓存中读取.");
				return imageAttr.isBaseImage()? htmlProblemMap.get(problemId):null;
			}
			if (imageAttr.isBaseImage()){
				/*尝试将从文件中读取到的题目数据写入缓存，如果此时缓存中已有，则以缓存中的为准*/
				ProblemShaVo problemShaVoInMap = htmlProblemMap.putIfAbsent(problemId, problemShaVoInFile);
				if (null==problemShaVoInMap){
					return problemShaVoInFile;
				}else{
					return problemShaVoInMap;
				}
			}else{
				return problemShaVoInFile;
			}
		} catch (FileNotFoundException e) {
			log.error("["+problemId+"]没有题目的详情保存文件，无法读取:");
		} catch (IOException e) {
			log.error("["+problemId+"]IO错误，无法读取题目的详情。",e);
		} catch (ClassNotFoundException e) {
			log.error("["+problemId+"]转换错误，无法读取题目的详情。",e);
		}catch (Exception e) {
			log.error("["+problemId+"]无法读取文件中题目的详情。",e);
		}
		return imageAttr.isBaseImage()? htmlProblemMap.get(problemId):null;
	}		
	
	private class ProblemTask implements Callable<ProblemShaVo>{
		private TbProblemContent problemContent;
		private ImgaeAttr imageAttr;
		
		public ProblemTask(TbProblemContent problemContent,ImgaeAttr imageAttr) {
			super();
			this.problemContent = problemContent;
			this.imageAttr = imageAttr;
		}

		@Override
		public ProblemShaVo call() throws Exception {
			try {
				log.info("["+problemContent.getId()+"]开始生成题目html公式文本......");
				long start = System.currentTimeMillis();
				ProblemShaVo problemShaVoNew = new ProblemShaVo(problemContent.getId());
				problemShaVoNew.setStemOrigin(getProblemStem(problemContent).toString());
				problemShaVoNew.setAnswerOrigin(problemContent.getAnswerContent());
				if (null==imageAttr){
					imageAttr = new ImgaeAttr();
				}
				problemShaVoNew.setStemHtml(makeLatexHtml(problemContent, false,imageAttr));
				problemShaVoNew.setAnswerHtml(makeLatexHtml(problemContent, true,imageAttr));
				problemShaVoNew.setStemSha(EncryptTools.EncryptBySHA1(problemShaVoNew.getStemOrigin()));
				problemShaVoNew.setAnswerSha(EncryptTools.EncryptBySHA1(problemShaVoNew.getAnswerOrigin()));
				problemShaVoNew.setHtmlSha(EncryptTools.EncryptBySHA1(problemShaVoNew.getStemHtml()
						+problemShaVoNew.getAnswerHtml()));
				if (imageAttr.isBaseImage()){
					htmlProblemMap.put(problemContent.getId(), problemShaVoNew);
				}
				saveProblemDetail(problemShaVoNew,imageAttr);
				log.info("["+problemContent.getId()+"]生成题目html公式文本耗时："+(System.currentTimeMillis()-start)+"ms.");
				return problemShaVoNew;
			} finally {
				pendingProblemMap.remove(makePendingProblemMapKey(imageAttr.getImageBaseAttr(),problemContent.getId()));
			}
		}
	}

	/**
	 * 以future方式生成题目的html串
	 * @param problemContent
	 * @param isAnswer
	 * @param operateType
	 * @param oldProblemShaVo
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	private Future<ProblemShaVo> getProblemFuture(TbProblemContent problemContent,String fileName,ImgaeAttr imageAttr) 
			throws InterruptedException, ExecutionException{
		final Long problemId = problemContent.getId();
		Future<ProblemShaVo> pendingProblem = pendingProblemMap.get(makePendingProblemMapKey(imageAttr.getImageBaseAttr(),problemId));
		try {
			if (null==pendingProblem){
				ProblemTask problemTask = new ProblemTask(problemContent,imageAttr);
				FutureTask<ProblemShaVo> ft = new FutureTask<ProblemShaVo>(problemTask);
				pendingProblem = pendingProblemMap.putIfAbsent(makePendingProblemMapKey(imageAttr.getImageBaseAttr(),problemId), ft);
				if (null==pendingProblem){
					pendingProblem = ft;
					//makeProblemExecutor.submit(ft);
					makeProblemExecutor.execute(ft);
				}
			}else{
				log.debug(fileName+"["+problemId+"]已存在题目的计算任务，等待完成.");
			}
			return pendingProblem;
		} catch(Exception e) {
			pendingProblemMap.remove(makePendingProblemMapKey(imageAttr.getImageBaseAttr(),problemContent.getId()));
			log.error("["+problemId+"]题目的计算任务异常:",e);
			throw e;
		}
	}
	
	/**
	 * 检查题目是否已经变化过，没变化过，从缓存中直接取出内容，变化过重新转Latex为html串
	 * @param problemContent 包含LaTeX内容的题目实体
	 * @return 转为html串的文本或生成html串的任务
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public QueryProblemContentVo checkAndGetProblem(final TbProblemContent problemContent,String fileName,ImgaeAttr imageAttr) 
			throws InterruptedException, ExecutionException{
		final Long problemId = problemContent.getId();
		ProblemShaVo problemShaVo = imageAttr.isBaseImage()? htmlProblemMap.get(problemId):null;
		if (null==problemShaVo) {
			log.debug(fileName+"["+problemId+"]在缓存中未找到，准备从文件中读取......");
			problemShaVo = loadProblemDetail(problemId,imageAttr);
			if (null==problemShaVo){
				log.debug(fileName+"["+problemId+"]在文件中未找到，准备生成......");
				return  new QueryProblemContentVo(null,getProblemFuture(problemContent,fileName,imageAttr));
			}			
		}
		
		/*到这里时，从缓存或文件中读取到了保存的题目详情，并刷新了题目html串的本地缓存
		 * 进行题目详情的sha校验*/		
		if (EncryptTools.EncryptBySHA1(getProblemStem(problemContent).toString()).equals(problemShaVo.getStemSha())
				&&EncryptTools.EncryptBySHA1(problemContent.getAnswerContent()).equals(problemShaVo.getAnswerSha())){
			return new QueryProblemContentVo(problemShaVo,null);
		}else{
			log.debug(fileName+"["+problemId+"]保存的题干或答案已和数据库中不一致，准备重新生成......");
			Future<ProblemShaVo> futureProblemShaVo = pendingProblemMap.get(makePendingProblemMapKey(imageAttr.getImageBaseAttr(),problemId));
			if (null!=futureProblemShaVo){
				log.debug(fileName+"["+problemId+"]已有任务正在生成中，等待完成.");
				return  new QueryProblemContentVo(null,futureProblemShaVo);
			}else{
				return  new QueryProblemContentVo(null,getProblemFuture(problemContent,fileName,imageAttr));
			}
		}

	}
	
	public static StringBuffer getProblemStem(TbProblemContent problemContent){
		StringBuffer result = new StringBuffer();
		result.append(problemContent.getProblemContent());
		if(0==problemContent.getType()){
			result.append(problemContent.getaContent()).append(problemContent.getbContent())
					.append(problemContent.getcContent()).append(problemContent.getdContent());
		}
		return result;
	}
	
	private String makeLatexHtml(TbProblemContent problemContent, boolean isAnswer, ImgaeAttr imageAttr) throws InterruptedException, ExecutionException{
		if (!isAnswer){
			return problemUtilsForWK.convertProblemContent2DivTag(problemContent,imageAttr);
		}else{
			return problemUtilsForWK.convertProblemAnswer2DivTag(problemContent,imageAttr);
		}
	}
	
}
