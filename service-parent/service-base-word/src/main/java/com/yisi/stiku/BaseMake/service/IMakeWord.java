package com.yisi.stiku.BaseMake.service;

import java.util.List;
import java.util.Map;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import com.yisi.stiku.MakeWord.Vo.StandardOneBook;
import com.yisi.stiku.MakeWord.Vo.XuanzheOptionVo;

public interface IMakeWord {
	/**
	 * 生成word版作业，每个具体的题干、答案、选择题选项内容均可包含ooxml格式的文本
	 * @param title 作业标题
	 * @param header 页眉
	 * @param footer 页脚
	 * @param xuanzheProblemStem 选择题题干列表，题目顺序即为列表中元素的顺序
	 * @param xuanzheProblemOptions 选择题选项Map，Key为xuanzheProblemStem中元素的顺序
	 * @param xuanzheAnswer 选择题答案列表，本列表中元素应与xuanzheProblemStem中元素一一对应
	 * @param tianKongProblemStem 填空题题干列表，题目顺序即为列表中元素的顺序
	 * @param tianKongAnswer 填空题答案列表，本列表中元素应与tianKongProblemStem中元素一一对应
	 * @param jieDaProblemStem 解答题题干列表，题目顺序即为列表中元素的顺序
	 * @param jieDaAnswer 解答题答案列表，本列表中元素应与jieDaProblemStem中元素一一对应
	 * @param fileName 生成作业的文件名
	 * @return word版作业是否生成成功
	 */
	public boolean makeWordStandardAll(String title,String header,String footer,
			String preface,
			List<String> xuanzheProblemStem,
			Map<Integer,XuanzheOptionVo> xuanzheProblemOptions,
			Map<Integer, XuanzheOptionVo> xuanzheAnswer,
			List<String> tianKongProblemStem,
			List<String> tianKongAnswer,
			List<String> jieDaProblemStem,
			List<String> jieDaAnswer,
			String postscript,
			String fileName);
	
	/**
	 * 生成单本标准的word文档
	 * @param title 文件标题
	 * @param header 页眉
	 * @param footer 页脚
	 * @param fileName 生成作业的文件名，包含路径,文件名为空，表示不保存
	 * @param oneBook 单本待生成的数据
	 * @param wmp
	 * @return
	 * @throws Exception
	 */
	public WordprocessingMLPackage makeStandardOneBook(String title,String header,String footer,String fileName,
			StandardOneBook oneBook,WordprocessingMLPackage wmp) throws Exception ;
	
	/**
	 * 生成包含多册内容的一份word文档
	 * @param header
	 * @param footer
	 * @param fileName
	 * @param oneBook
	 * @return
	 */
	public boolean makeStandardBooks(List<String> title,String header,String footer,String fileName,
			List<StandardOneBook> books);
}
