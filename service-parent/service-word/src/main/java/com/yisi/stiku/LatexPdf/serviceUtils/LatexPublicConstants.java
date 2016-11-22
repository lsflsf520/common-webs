package com.yisi.stiku.LatexPdf.serviceUtils;

public class LatexPublicConstants {
	
	/**LaTeX转pdf时，一般错误代码*/
	public final static int Error_Code = -1;
	/**题目的latex字段为空时错误代码*/
	public final static int Empty_Error_Code = -100;
	
	/**生成提分密案的html模板名*/
	public final static String Templet_MonthMagazine = "monthMagazine.html";
	/**生成提分密案两本版的html模板名*/
	public final static String Templet_MonthMagazine_TwoBooks = "monthMagazineTwoBooks.html";	
	/**生成考前冲刺的html模板名*/
	public final static String Templet_SprintMagazine = "sprintMagazine.html";
	/**生成提分特训的html模板名*/
	public final static String Templet_SpecialTraining = "specialTraining.html";
	
	/**以指定字符包围的数字标识是需要替换为实际题目的内容*/
	public final static String Split_Symbol = "@";
	/**以指定字符包围的数字标识是需要替换为实际题目题干的内容*/
	public final static String Split_Symbol_ProblemStem = "~~";
	/**以指定字符包围的数字标识是需要替换为实际题目答案的内容*/
	public final static String Split_Symbol_Answer = "``";	
	/** 处理题目转html串时的应答信息*/
	public final static String MAKE_PROBLEM_HTML_SUCESS = "SUCCESS";
	public final static String MAKE_PROBLEM_HTML_ERROR = "ERROR:";
	public final static String MAKE_PROBLEM_HTML_RETRY = "RETRY:";
}
