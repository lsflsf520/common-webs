// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst 
// Source File Name:   SchedulerDTO.java

package com.yisi.stiku.quartz.web.vo;

import java.util.ArrayList;
import java.util.List;

public class SchedulerDTO {

	private String schedulerName;
	private String runningSince;
	private String numJobsExecuted;
	private String persistenceType;
	private String threadPoolSize;
	private String version;
	private String state;
	private String summary;
	private List globalJobListeners;
	private List globalTriggerListeners;
	private List schedulerListeners;
	private List registeredJobListeners;
	private List registeredTriggerListeners;

	public SchedulerDTO() {
		schedulerListeners = new ArrayList();
		globalJobListeners = new ArrayList();
		globalTriggerListeners = new ArrayList();
		registeredJobListeners = new ArrayList();
		registeredTriggerListeners = new ArrayList();
	}

	public String getNumJobsExecuted() {
		return numJobsExecuted;
	}

	public String getPersistenceType() {
		return persistenceType;
	}

	public String getRunningSince() {
		return runningSince;
	}

	public String getSchedulerName() {
		return schedulerName;
	}

	public String getState() {
		return state;
	}

	public String getThreadPoolSize() {
		return threadPoolSize;
	}

	public String getVersion() {
		return version;
	}

	public void setNumJobsExecuted(String numJobsExecuted) {
		this.numJobsExecuted = numJobsExecuted;
	}

	public void setPersistenceType(String persistenceType) {
		this.persistenceType = persistenceType;
	}

	public void setRunningSince(String runningSince) {
		this.runningSince = runningSince;
	}

	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setThreadPoolSize(String threadPool) {
		threadPoolSize = threadPool;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List getGlobalJobListeners() {
		return globalJobListeners;
	}

	public List getGlobalTriggerListeners() {
		return globalTriggerListeners;
	}

	public List getSchedulerListeners() {
		return schedulerListeners;
	}

	public void setGlobalJobListeners(ArrayList globalJobListeners) {
		this.globalJobListeners = globalJobListeners;
	}

	public void setGlobalTriggerListeners(ArrayList globalTriggerListeners) {
		this.globalTriggerListeners = globalTriggerListeners;
	}

	public void setSchedulerListeners(ArrayList schedulerListeners) {
		this.schedulerListeners = schedulerListeners;
	}

	public List getRegisteredJobListeners() {
		return registeredJobListeners;
	}

	public List getRegisteredTriggerListeners() {
		return registeredTriggerListeners;
	}

	public void setRegisteredJobListeners(ArrayList registeredJobListeners) {
		this.registeredJobListeners = registeredJobListeners;
	}

	public void setRegisteredTriggerListeners(
			ArrayList registeredTriggerListeners) {
		this.registeredTriggerListeners = registeredTriggerListeners;
	}
}
