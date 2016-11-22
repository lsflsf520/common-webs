package com.yisi.stiku.LatexPdf.service;

import com.yisi.stiku.LatexPdf.Vo.HtmlProblemVo;
import com.yisi.stiku.LatexPdf.Vo.ImgaeAttr;

public interface IMakeProblemHtml {
	public HtmlProblemVo makeProblemIdHtml(Long problemId,ImgaeAttr imageAttr);
}
