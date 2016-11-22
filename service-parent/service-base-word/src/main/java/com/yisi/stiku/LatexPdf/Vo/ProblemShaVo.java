package com.yisi.stiku.LatexPdf.Vo;

import java.io.Serializable;

public class ProblemShaVo  implements Serializable{
	private static final long serialVersionUID = 4519568945343072470L;
	private Long problemId;
	private String stemSha;
	private String answerSha;
	private String stemOrigin;
	private String answerOrigin;
	private String stemHtml;
	private String answerHtml;
	private String htmlSha;
	
	public ProblemShaVo(Long problemId) {
		super();
		this.problemId = problemId;
	}
	
	public Long getProblemId() {
		return problemId;
	}

	public String getStemSha() {
		return stemSha;
	}
	public void setStemSha(String stemSha) {
		this.stemSha = stemSha;
	}
	public String getAnswerSha() {
		return answerSha;
	}
	public void setAnswerSha(String answerSha) {
		this.answerSha = answerSha;
	}

	public String getStemOrigin() {
		return stemOrigin;
	}

	public void setStemOrigin(String problemOrigin) {
		this.stemOrigin = problemOrigin;
	}

	public String getAnswerOrigin() {
		return answerOrigin;
	}

	public void setAnswerOrigin(String answerOrigin) {
		this.answerOrigin = answerOrigin;
	}

	public String getStemHtml() {
		return stemHtml;
	}

	public void setStemHtml(String problemHtml) {
		this.stemHtml = problemHtml;
	}

	public String getAnswerHtml() {
		return answerHtml;
	}

	public void setAnswerHtml(String answerHtml) {
		this.answerHtml = answerHtml;
	}

	public String getHtmlSha() {
		return htmlSha;
	}

	public void setHtmlSha(String htmlSha) {
		this.htmlSha = htmlSha;
	}
	
	
}
