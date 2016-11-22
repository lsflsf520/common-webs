package com.yisi.stiku.stat.entity;

import java.util.List;

/**
 * @author shangfeng
 *
 */
public class TableData {

	/**
	 * 表格标题列表
	 */
	private List<String> colTitleList;

	private String tableTitle;

	private int pageNum;

	public String getTableTitle() {

		return tableTitle;
	}

	public void setTableTitle(String tableTitle) {

		this.tableTitle = tableTitle;
	}

	public List<String> getColTitleList() {

		return colTitleList;
	}

	public void setColTitleList(List<String> colTitleList) {

		this.colTitleList = colTitleList;
	}

	public int getPageNum() {

		return pageNum;
	}

	public void setPageNum(int pageNum) {

		this.pageNum = pageNum;
	}

}
