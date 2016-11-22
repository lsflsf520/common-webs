package com.yisi.stiku.LatexPdf.Vo;

import java.util.List;

public class DocContentVo {

	private final String title;
	private final String header;
	private final String footer;
	private final String fileName;
	private final String contentsHtml;
	private final String answersHtml;
	private final List<Long> problemSet;
	private final List<Long> problemNoSet;
	private final boolean showPageNum;
	private final boolean showAnswer;

	/**
	 * @param title
	 *            文档的标题，为空则不生成
	 * @param header
	 *            页眉，为空则不生成
	 * @param footer
	 *            页脚，为空则不生成
	 * @param fileName
	 *            需生成的文件名
	 * @param contentsHtml
	 *            文件中需要填充的题目的自定义格式内容，可以为空
	 * @param answersHtml
	 *            文件中需要填充的答案解析部分自定义格式内容，可以为空
	 * @param problemSet
	 *            文档中题目id的聚集
	 * @param problemNoSet
	 *            文档中题目题号的聚集
	 * @param showPageNum
	 *            是否在pdf中打印页码
	 */
	public DocContentVo(String title, String header, String footer,
			String fileName, String contentsHtml, String answersHtml,
			List<Long> problemSet, List<Long> problemNoSet, boolean showPageNum) {

		super();
		this.title = title;
		this.header = header;
		this.footer = footer;
		this.fileName = fileName;
		this.contentsHtml = contentsHtml;
		this.answersHtml = answersHtml;
		this.problemSet = problemSet;
		this.problemNoSet = problemNoSet;
		this.showPageNum = showPageNum;
		this.showAnswer = true;
	}

	public DocContentVo(String title, String header, String footer,
			String fileName, String contentsHtml, String answersHtml,
			List<Long> problemSet, List<Long> problemNoSet, boolean showPageNum, boolean showAnswer) {

		super();
		this.title = title;
		this.header = header;
		this.footer = footer;
		this.fileName = fileName;
		this.contentsHtml = contentsHtml;
		this.answersHtml = answersHtml;
		this.problemSet = problemSet;
		this.problemNoSet = problemNoSet;
		this.showPageNum = showPageNum;
		this.showAnswer = showAnswer;
	}

	public String getTitle() {

		return title;
	}

	public String getHeader() {

		return header;
	}

	public String getFooter() {

		return footer;
	}

	public String getFileName() {

		return fileName;
	}

	public String getContentsHtml() {

		return contentsHtml;
	}

	public String getAnswersHtml() {

		return answersHtml;
	}

	public List<Long> getProblemSet() {

		return problemSet;
	}

	public boolean isShowPageNum() {

		return showPageNum;
	}

	public List<Long> getProblemNoSet() {

		return problemNoSet;
	}

	public boolean isShowAnswer() {

		return showAnswer;
	}

}
