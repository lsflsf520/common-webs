package com.yisi.stiku.LatexPdf.utils;

import com.yisi.stiku.utils.OffLineDocBaseConstant;

public class LatexPdfBaseConstant {
	
	public final static String Success = "Success";
	public final static String Failure = "Unknown Failure";
	
	public final static String PDF_EXTENSION = ".pdf";
	public final static String HTML_EXTENSION = ".html";
	public final static String WorkDir_PostFix = "Pdf/";
	
	/**latex转pdf时，公式图片的临时存放目录*/
	public final static String PDF_TEMP_IMG_DIR =  "PdfFormulaImage/";
	/**全路径，latex转pdf时，公式图片的临时存放目录*/
	public final static String PDF_TEMP_IMG_FULLPATH =  OffLineDocBaseConstant.USER_HOME + PDF_TEMP_IMG_DIR;
	
	/**存放生成pdf的html模板文件的目录*/
	public final static String PDF_TEMPLET_DIR =  "PdfTemplet/";
	/**全路径，存放生成pdf的html模板文件的目录*/
	public final static String PDF_TEMPLET_FULLPATH =  OffLineDocBaseConstant.USER_HOME + PDF_TEMPLET_DIR;
	
	/*************************************
	 * 同时进行的任务数
	 *************************************/
	/**
	 * 同时处理的作业 数
	 */
	public static final int SAME_TIME_QUEUR = Runtime.getRuntime().availableProcessors();
	/**
	 * 同时处理的题目数
	 */	
	public static final int SAME_TIME_PROBLEMS = Runtime.getRuntime().availableProcessors()*2;
	/**
	 * 同时处理的题目中的公式数
	 */
	public static final int SAME_TIME_LATEX_FORMULA = Runtime.getRuntime().availableProcessors()*2;
	
}
