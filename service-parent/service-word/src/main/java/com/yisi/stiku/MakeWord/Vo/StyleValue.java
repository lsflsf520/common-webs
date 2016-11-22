package com.yisi.stiku.MakeWord.Vo;

import java.math.BigInteger;

import com.yisi.stiku.MakeWord.serviceutils.WordPublicConstant;

/**
 * 自然段文本的格式，目前只支持整段文字的字体设置和大小、加粗、斜体、下划线，段落的对齐方式
 * @author mk
 *
 */
public class StyleValue {
	private String fontName = "微软雅黑";
	private int fontSize = 18;
	private boolean bold = false;
	private boolean italic = false;
	private boolean underline = false;
	private String align = WordPublicConstant.ALIGN_LEFT;
	
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public StyleValue() {
		super();
	}
	public StyleValue(String fontName) {
		super();
		this.fontName = fontName;
	}
	public StyleValue(int fontSize) {
		super();
		this.fontSize = fontSize;
	}
	public StyleValue(String fontName, int fontSize) {
		super();
		this.fontName = fontName;
		this.fontSize = fontSize;
	}
	public StyleValue(String fontName, int fontSize, boolean bold,
			boolean italic, boolean underline) {
		super();
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.bold = bold;
		this.italic = italic;
		this.underline = underline;
	}
	
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public boolean isBold() {
		return bold;
	}
	public void setBold(boolean bold) {
		this.bold = bold;
	}
	public boolean isItalic() {
		return italic;
	}
	public void setItalic(boolean italic) {
		this.italic = italic;
	}
	public boolean isUnderline() {
		return underline;
	}
	public void setUnderline(boolean underline) {
		this.underline = underline;
	}
	
}
