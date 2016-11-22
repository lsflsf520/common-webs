package com.yisi.stiku.quartz.dao.entity;

import java.io.Serializable;

import org.quartz.JobKey;

public class TaskPK implements Serializable {

	private static final long serialVersionUID = 7528275159688837750L;
	private String schedName;
	private JobKey jobkey;

	public TaskPK() {

	}

	public TaskPK(String schedName, JobKey jobkey) {
		this.schedName = schedName;
		this.jobkey = jobkey;
	}

	public String getSchedName() {
		return schedName;
	}

	public void setSchedName(String schedName) {
		this.schedName = schedName;
	}

	public JobKey getJobkey() {
		return jobkey;
	}

	public void setJobkey(JobKey jobkey) {
		this.jobkey = jobkey;
	}
}
