package com.yisi.stiku.LatexPdf.service;

import java.util.Map;
import java.util.Set;

import com.yisi.stiku.LatexPdf.Vo.MakePdfResultVo;

public interface IMakeLatexPdf {

	/**
	 * 生成一份由LaTeX转pdf的文档(带自定义参数)
	 * 
	 * @param header
	 *            页眉
	 * @param footer
	 *            页脚
	 * @param workDir
	 *            生成的文件存放路径
	 * @param fileName
	 *            生成时文件名，不包含文件的路径和文件的后缀
	 * @param templetFileName
	 *            html模板文件名
	 * @param contents
	 *            模板中需替换的内容，一般包含题目
	 * @param answers
	 *            模板中需替换的内容，一般包含题目的解答
	 * @param problemSet
	 *            内容中所使用的题目集
	 * @param showPageNum
	 *            打印pdf时是否有页码
	 * @param showPageNum
	 *            打印pdf时是否有页码
	 * @return 成功返回生成的pdf全路径，失败返回失败信息
	 */
	public MakePdfResultVo makeNormalLatexPdf(String header, String footer, String workDir, String fileName,
			String templetFileName, Map<String, String> problems, Map<String, String> answers, Set<Long> problemSet,
			boolean showPageNum, String customCommand) throws Exception;
}
