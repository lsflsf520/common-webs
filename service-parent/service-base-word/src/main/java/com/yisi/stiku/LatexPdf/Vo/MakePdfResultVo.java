package com.yisi.stiku.LatexPdf.Vo;

public class MakePdfResultVo {
	private int pageNums;
	private String pdfName;
	
	public MakePdfResultVo(String pdfName) {
		super();
		this.pdfName = pdfName;
		this.pageNums = 0;
	}
	public int getPageNums() {
		return pageNums;
	}
	public void setPageNums(int pageNums) {
		this.pageNums = pageNums;
	}
	public String getPdfName() {
		return pdfName;
	}
	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}
	public MakePdfResultVo fillFailureReason(String errorMsg){
		this.pdfName = errorMsg;
		this.pageNums = 0;
		return this;
	}
}
