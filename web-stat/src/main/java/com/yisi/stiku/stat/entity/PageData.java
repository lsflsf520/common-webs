package com.yisi.stiku.stat.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shangfeng
 *
 */
public class PageData {

	private String url;

	private Chart chart;

	private TableData tableData;

	// 用于分页的原始db数据
	private List<Map<String, Object>> primDbDataList;

	/**
	 * 数据字典
	 */
	private Map<String, String> dataDictMap = new HashMap<String, String>();

	public Chart getChart() {

		return chart;
	}

	public void setChart(Chart chart) {

		this.chart = chart;
	}

	public List<Map<String, Object>> getPrimDbDataList() {

		return primDbDataList;
	}

	public void setPrimDbDataList(List<Map<String, Object>> primDbDataList) {

		this.primDbDataList = primDbDataList;
	}

	public void addDataDictMap(Map<String, String> dataDictMap) {

		if (dataDictMap != null && !dataDictMap.isEmpty()) {
			this.dataDictMap.putAll(dataDictMap);
		}
	}

	public String getUrl() {

		return url;
	}

	public void setUrl(String url) {

		this.url = url;
	}

	public TableData getTableData() {

		return tableData;
	}

	public void setTableData(TableData tableData) {

		this.tableData = tableData;
	}

	public Map<String, String> getDataDictMap() {

		return Collections.unmodifiableMap(dataDictMap);
	}

}
