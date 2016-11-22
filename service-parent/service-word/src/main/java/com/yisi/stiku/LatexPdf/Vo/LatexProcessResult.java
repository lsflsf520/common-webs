package com.yisi.stiku.LatexPdf.Vo;

public class LatexProcessResult {
	private DocAttrVo docAttrWork;
	private String downloadPath;
	private int pageNums;
	
	public String getDownloadPath() {
		return downloadPath;
	}
	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}
	public int getPageNums() {
		return pageNums;
	}
	public void setPageNums(int pageNums) {
		this.pageNums = pageNums;
	}
	public DocAttrVo getDocAttrWork() {
		return docAttrWork;
	}
	public void setDocAttrVo(DocAttrVo docAttrWork) {
		this.docAttrWork = docAttrWork;
	}
	
}
