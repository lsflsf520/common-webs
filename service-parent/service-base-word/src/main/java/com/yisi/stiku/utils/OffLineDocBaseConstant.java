package com.yisi.stiku.utils;

public class OffLineDocBaseConstant {
	
	/**用户的home目录*/
	public final static String USER_HOME =  System.getProperty("user.home") + "/";
	
	/**生成文档时需下载的图片存放目录*/
	public final static String LOCAL_IMAGE_CACHE_DIR = "localImageCache/";
	/**全路径，生成文档时需下载的图片存放目录*/
	public final static String LOCAL_IMAGE_CACHE_FULLPATH = USER_HOME+LOCAL_IMAGE_CACHE_DIR;
	
	public final static String PROBLEM_DETAIL_DIR = "problmeDetail/";
	public final static String PROBLEM_DETAIL_FULLPATH = USER_HOME+PROBLEM_DETAIL_DIR;
	public final static int DIR_COUNTS= 500;
	public final static String PROBLEM_DETAIL_EXTENSION = ".detail";
	//public final static String PROBLEM_EXTENSION = ".data";
	public final static String ANSWER_POSTFIX = "Answer";
	public final static String SHA_POSTFIX = "Sha";
}
