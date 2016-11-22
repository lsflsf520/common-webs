package com.yisi.stiku.stat.entity;

import java.util.List;

/**
 * @author shangfeng
 *
 */
public class Chart {

	private String divId;

	private String title;
	private String subTitle;

	private String titleX;
	private String titleY;

	private List<String> legendData;

	private List<String> axisXData;
	private List<String> axisYData;

	private List<Sery> series;

	public String getDivId() {

		return divId;
	}

	public void setDivId(String divId) {

		this.divId = divId;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public String getSubTitle() {

		return subTitle;
	}

	public void setSubTitle(String subTitle) {

		this.subTitle = subTitle;
	}

	public String getTitleX() {

		return titleX;
	}

	public void setTitleX(String titleX) {

		this.titleX = titleX;
	}

	public String getTitleY() {

		return titleY;
	}

	public void setTitleY(String titleY) {

		this.titleY = titleY;
	}

	public List<String> getLegendData() {

		return legendData;
	}

	public void setLegendData(List<String> legendData) {

		this.legendData = legendData;
	}

	public List<String> getAxisXData() {

		return axisXData;
	}

	public void setAxisXData(List<String> axisXData) {

		this.axisXData = axisXData;
	}

	public List<String> getAxisYData() {

		return axisYData;
	}

	public void setAxisYData(List<String> axisYData) {

		this.axisYData = axisYData;
	}

	public List<Sery> getSeries() {

		return series;
	}

	public void setSeries(List<Sery> series) {

		this.series = series;
	}

}
