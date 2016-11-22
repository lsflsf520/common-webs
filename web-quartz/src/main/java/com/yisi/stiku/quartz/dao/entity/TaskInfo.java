package com.yisi.stiku.quartz.dao.entity;

import java.sql.Date;

public class TaskInfo {
	private Date beginTime;
	private Date endTime;
	private String ipaddress;
	private String macAddress;

	public TaskInfo(Date beginTime, Date endTime, String ipaddress,
			String macAddress) {
		super();
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.ipaddress = ipaddress;
		this.macAddress = macAddress;
	}

	public TaskInfo(Date beginTime, String ipaddress, String macAddress) {
		super();
		this.beginTime = beginTime;

		this.ipaddress = ipaddress;
		this.macAddress = macAddress;
	}

	public TaskInfo(Date beginTime) {
		super();
		this.beginTime = beginTime;

	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

}
