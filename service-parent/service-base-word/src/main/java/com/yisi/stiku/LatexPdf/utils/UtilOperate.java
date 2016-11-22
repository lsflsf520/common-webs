package com.yisi.stiku.LatexPdf.utils;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilOperate {
	
	private static Logger log = LoggerFactory.getLogger(UtilOperate.class);
	
	public static void checkDirAndCreate(String dirPath){
		if (null==dirPath||"".equals(dirPath))
			return;
		File downloadPath = new File(dirPath);
		if (!downloadPath.exists())	{
			downloadPath.mkdirs();
			log.info(dirPath+"不存在，已创建。");
		}
	}
	
	public static boolean checkDir(String dirPath){
		if (null==dirPath||"".equals(dirPath))
			return false;
		File downloadPath = new File(dirPath);
		if (!downloadPath.exists())	{
			return false;
		}else{
			return true;
		}
	}
}
