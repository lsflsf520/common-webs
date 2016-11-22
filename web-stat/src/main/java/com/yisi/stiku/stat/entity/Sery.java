package com.yisi.stiku.stat.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author shangfeng
 *
 */
public class Sery {

	private String name;
	private String type;
	private List<Object> dataList = new ArrayList<Object>();

	/**
	 * 
	 */
	public Sery(String type) {

		this.type = type;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getType() {

		return type;
	}

	public List<Object> getDataList() {

		return dataList;
	}

	public void addAllData(List<Object> dataList) {

		if (dataList != null && !dataList.isEmpty()) {
			this.dataList.addAll(dataList);
		}
	}

	public void addData(Object data) {

		this.dataList.add(data);
	}

	@Override
	public boolean equals(Object obj) {

		Sery sery = null;
		return obj instanceof Sery && StringUtils.isNotBlank(this.getName())
				&& this.getName().equals((sery = (Sery) obj).getName()) && StringUtils.isNotBlank(this.getType())
				&& this.getType().equals(sery.getType());
	}
}
