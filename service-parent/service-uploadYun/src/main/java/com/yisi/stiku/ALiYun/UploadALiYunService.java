package com.yisi.stiku.ALiYun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.ServiceException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSErrorCode;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.CannedAccessControlList;
import com.aliyun.openservices.oss.model.OSSObjectSummary;
import com.aliyun.openservices.oss.model.ObjectListing;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.yisi.stiku.vo.UploadResultVo;


@Service
public class UploadALiYunService {

	/*等待上传的队列*/
	private BlockingQueue<UploadFile> readyUploadQueue = new LinkedBlockingQueue<UploadFile>(
			3000);
	/*上传结果map
	 * key = SystemName
	 */
	private ConcurrentHashMap<String, BlockingQueue<UploadResultVo>> uploadResultMap = new ConcurrentHashMap<String, BlockingQueue<UploadResultVo>>();
	private volatile boolean uploadWorkFlag = true;
	
	private static Logger log = LoggerFactory.getLogger(UploadALiYunService.class);
	
	private class UploadFile {
		String fileURL;
		String fileKey;
		String bucket;
		
		/*与外部关联的系统名和id，不为空一般表示上传结果需要写入结果队列
		 * 如，生成提分密案时，该id为homeworkId*/
		String systemName;
		String outterId;

		public UploadFile(String fileURL, String fileKey, String bucket,
				String systemName,String outterId) {
			super();
			this.fileURL = fileURL;
			this.fileKey = fileKey;
			this.bucket = bucket;
			this.systemName = systemName;
			this.outterId = outterId;
		}
		
		public String getSystemName() {
			return systemName;
		}

		public String getOutterId() {
			return outterId;
		}

		public UploadFile(String fileURL, String fileKey, String bucket) {
			super();
			this.fileURL = fileURL;
			this.fileKey = fileKey;
			this.bucket = bucket;
		}

		public String getBucket() {
			return bucket;
		}

		public String getFileURL() {
			return fileURL;
		}

		public String getFileKey() {
			return fileKey;
		}

	}

	private class UploadWork implements Runnable {

		@Override
		public void run() {
			String threadName = "-------"
					+ Thread.currentThread().getName() + " ";
			while (uploadWorkFlag) {
				try {
					UploadFile uploadFile = readyUploadQueue.take();
					if (null != uploadFile) {
						long timeTestEnd = System.currentTimeMillis();// 记录结束时间

						log.info(threadName + "-------准备上传["
								+ uploadFile.getFileKey() + "]");
						try {
							UploadALiYun.IsOSSOUpload(uploadFile.getBucket(),
									uploadFile.getFileURL(),
									uploadFile.getFileKey());
							if (null!=uploadFile.getSystemName()&&null!=uploadFile.getOutterId()){ 
								uploadResultMap.get(uploadFile.getSystemName()).put(new UploadResultVo(uploadFile.getOutterId(),true));
								//uploadResultMap.putIfAbsent(uploadFile.getOutterId(),SUCCESS);		
							}
						} catch (Exception e) {
							log.error(threadName + "上传失败！", e);
							if (null!=uploadFile.getOutterId())
								uploadResultMap.get(uploadFile.getSystemName()).put(new UploadResultVo(uploadFile.getOutterId(),false));
								//uploadResultMap.putIfAbsent(uploadFile.getOutterId(),FAILURE);							
						}
						log.info(threadName + "-------["
								+ uploadFile.getFileKey() + "]上传耗时："
								+ (System.currentTimeMillis() - timeTestEnd)
								+ " ms.写入"+uploadFile.getSystemName()+"上传结果队列。");
					}
				} catch (InterruptedException e) {
					log.error(threadName + "线程被中断！", e);
				}
			}
			log.info(threadName + " thread was closed -------");
		}
	}

	@PostConstruct	
	public void startUploadWork() {
		for(int i=0;i<2;i++){
			Thread uploadFile = new Thread(new UploadWork(),"uploadAiYun_"+i);
			log.info("------ Upload " + uploadFile.getName()
					+ " thread is beginning-------");
			uploadFile.start();			
		}
	}

	@PreDestroy
	public void stopUploadWork() {
		uploadWorkFlag = false;
	}

	public void registerQueue(String SystemName,BlockingQueue<UploadResultVo> registerQueue){
		log.info(SystemName+"被注册到上传模块UploadALiYun！");
		uploadResultMap.put(SystemName, registerQueue);
	}
	
	public void UnRegisterQueue(String SystemName){
		log.info(SystemName+"从上传模块UploadALiYun中取消注册！");
		uploadResultMap.remove(SystemName);
	}
	
	/**
	 * 队列模式上传，上传结果无需传出
	 * @param bucket 阿里云上域名前缀： 目前为："17daxue-magazine"
	 * @param fileURL 文件本地全路径
	 * @param fileKey 文件名
	 * @throws Exception
	 */
	public void outterUploadMethod(String bucket, String fileURL, String fileKey)
			throws Exception {
		UploadFile pdfFile = new UploadFile(fileURL, fileKey, bucket);
		readyUploadQueue.put(pdfFile);
	}

	/**
	 * 队列模式上传，上传结果需传出
	 * @param bucket 阿里云上域名前缀： 目前为："17daxue-magazine"
	 * @param fileURL 文件本地全路径
	 * @param fileKey 文件名
	 * @param outterId 与外部关联的id
	 * @throws Exception
	 */
	public void outterUploadMethod(String bucket, String fileURL, String fileKey,String systemName,String outterId)
			throws Exception {
		if (null==systemName||null==outterId||"".equals(systemName)||"".equals(outterId)){
			throw new Exception("系统名和对应id均不允许为空");
		}
		if (null==uploadResultMap.get(systemName)){
			throw new Exception("你的系统尚未在系统中注册！");
		}
		UploadFile pdfFile = new UploadFile(fileURL, fileKey, bucket,systemName,outterId);
		readyUploadQueue.put(pdfFile);
		//uploadResultMap.putIfAbsent(outterId,WAIT_UPLOAD);
	}	

}
