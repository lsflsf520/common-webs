package com.yisi.stiku.MakeWord.Vo;

import java.math.BigInteger;

/**
 * 文本和格式
 * @author mk
 *
 */
public class StyleTextVo {
	private String txt;
	private StyleValue styleValue;
	
	public StyleTextVo(String txt) {
		super();
		this.txt = txt;
	}
	public StyleTextVo(String txt, StyleValue styleValue) {
		super();
		this.txt = txt;
		this.styleValue = styleValue;
	}
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
	public StyleValue getStyleValue() {
		return styleValue;
	}
	public void setStyleValue(StyleValue styleValue) {
		this.styleValue = styleValue;
	}
	

/*	public R makeStyleText(ObjectFactory factory){
		return makeStyleText(factory,this.txt,this.styleValue);
	}*/
}
