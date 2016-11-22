package com.yisi.stiku.MakeWord.service;

import java.util.List;
import java.util.Map;

import com.yisi.stiku.MakeWord.Vo.StandardOneBook;

public interface IComplexWordRpcService {
	
	/**
	 * 生成包含多册内容的一份word文档
	 * @param title 每册的标题
	 * @param header 页眉
	 * @param footer 页脚
	 * @param fileName 生成的文件名
	 * @param books 包含需要生成word的的内容
	 * @return 
	 */
	public String makeStandardBooks(List<String> title,String header,String footer,String fileName,
			List<StandardOneBook> books);
}
