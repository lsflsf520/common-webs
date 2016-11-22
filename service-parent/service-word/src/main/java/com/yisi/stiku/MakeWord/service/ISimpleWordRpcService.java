package com.yisi.stiku.MakeWord.service;

import java.util.List;

public interface ISimpleWordRpcService {
	/**
	 * 生成普通word文档
	 * @param docTitle 文档标题
	 * @param problemIdList 题目id列表，
	 * @param fileName 文档文件名
	 * @param showIndexNo 是否显示每个题目在文档中排序号
	 * @param showProblemSeq 是否显示每个题目的查询号
	 * @param showProblem 是否显示题目
	 * @param showAnswer 是否显示题目的答案
	 * @param uploadALiYun 是否将生成的文档上传至阿里云
	 * @return word文件的本地目录（ifUploadALiYun=false）或者在阿里云的下载地址
	 */
	public String makeSimpleDoc(String docTitle,List<Long> problemIdList,String fileName,
			boolean showIndexNo,boolean showProblemSeq,boolean showProblem,boolean showAnswer,
			boolean uploadALiYun);
}
