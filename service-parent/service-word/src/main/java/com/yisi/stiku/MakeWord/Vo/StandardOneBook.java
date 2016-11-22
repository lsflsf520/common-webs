package com.yisi.stiku.MakeWord.Vo;

import java.util.List;

/**
 * 包含单本标准的word文档可具备的子元素<br>
 * 允许元素缺失，在实际写往word文档时，
 * 判断该元素为null时，不予处理。
 * @author mk
 *
 */
public class StandardOneBook {
	
	/*封面,如果String格式为<img ... />表示为图片*/
	List<String> coverList;
	/*前言*/
	StandardSegment<StandardSegment<?>> preface;
	/*题目区*/
	StandardSegment<StandardSegment<?>> problemZone;
	/*答案区*/
	StandardSegment<StandardSegment<?>> answerZone;
	/*后记*/
	StandardSegment<StyleTextVo> postscript;

	public List<String> getCoverList() {
		return coverList;
	}

	public void setCoverList(List<String> coverList) {
		this.coverList = coverList;
	}

	public StandardSegment<StandardSegment<?>> getPreface() {
		return preface;
	}

	public void setPreface(StandardSegment<StandardSegment<?>> preface) {
		this.preface = preface;
	}

	public StandardSegment<StandardSegment<?>> getProblemZone() {
		return problemZone;
	}

	public void setProblemZone(StandardSegment<StandardSegment<?>> problemZone) {
		this.problemZone = problemZone;
	}

	public StandardSegment<StandardSegment<?>> getAnswerZone() {
		return answerZone;
	}

	public void setAnswerZone(StandardSegment<StandardSegment<?>> answerZone) {
		this.answerZone = answerZone;
	}

	public StandardSegment<StyleTextVo> getPostscript() {
		return postscript;
	}

	public void setPostscript(StandardSegment<StyleTextVo> postscript) {
		this.postscript = postscript;
	}
	
	
}
