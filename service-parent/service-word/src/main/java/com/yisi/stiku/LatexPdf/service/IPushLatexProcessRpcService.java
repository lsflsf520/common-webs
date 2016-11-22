package com.yisi.stiku.LatexPdf.service;

import com.yisi.stiku.LatexPdf.Vo.LatexProcessResult;

public interface IPushLatexProcessRpcService {
	
	/**
	 * 将学生生成的作业结果返回给调用者
	 * @return
	 */
	public void pushNormalResult(LatexProcessResult result,String templetFileName);
}
