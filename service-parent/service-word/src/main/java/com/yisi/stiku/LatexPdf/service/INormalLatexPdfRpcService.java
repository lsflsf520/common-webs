package com.yisi.stiku.LatexPdf.service;

import java.util.Map;
import java.util.Set;

import com.yisi.stiku.LatexPdf.Vo.LatexProcessResult;
import com.yisi.stiku.LatexPdf.Vo.DocAttrVo;

public interface INormalLatexPdfRpcService {

	/**
	 * 用LaTeX生成pdf文件
	 * @param header 页眉
	 * @param footer 页脚
	 * @param fileName 需生成的文件名
	 * @param templetFileName 生成文件所依靠的模板名
	 * @param contents 文件中需要填充的实际内容，一般为包含题目的部分
	 * @param answers 文件中需要填充的实际内容，一般为答案解析部分
	 * @param showPageNum 是否在pdf中打印页码
	 * @return rpc服务端是否已成功接收任务，成功true，失败false
	 */
	public String makeLatexPdf(String header,String footer,String fileName,String templetFileName,
			Map<String,String> contents, Map<String,String> answers,Set<Long> problemSet,boolean showPageNum,
			DocAttrVo studentLatexWork);
}
