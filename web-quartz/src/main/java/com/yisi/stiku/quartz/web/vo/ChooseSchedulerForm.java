package com.yisi.stiku.quartz.web.vo;

import java.util.List;

import org.quartz.Scheduler;

public class ChooseSchedulerForm {

	private String choosenSchedulerName;
	private List<Scheduler> schedulers;
	private SchedulerDTO scheduler;
	private List<JobDetailForm> executingJobs;
	private String btnSetSchedulerAsCurrent;

	public ChooseSchedulerForm() {
	}

	public String getChoosenSchedulerName() {
		return choosenSchedulerName;
	}

	public void setChoosenSchedulerName(String choosenSchedulerName) {
		this.choosenSchedulerName = choosenSchedulerName;
	}

	public List<Scheduler> getSchedulers() {
		return schedulers;
	}

	public void setSchedulers(List<Scheduler> schedulers) {
		this.schedulers = schedulers;
	}

	public SchedulerDTO getScheduler() {
		return scheduler;
	}

	public void setScheduler(SchedulerDTO scheduler) {
		this.scheduler = scheduler;
	}

	public List<JobDetailForm> getExecutingJobs() {
		return executingJobs;
	}

	public void setExecutingJobs(List<JobDetailForm> executingJobs) {
		this.executingJobs = executingJobs;
	}

	public String getBtnSetSchedulerAsCurrent() {
		return btnSetSchedulerAsCurrent;
	}

	public void setBtnSetSchedulerAsCurrent(String btnSetSchedulerAsCurrent) {
		this.btnSetSchedulerAsCurrent = btnSetSchedulerAsCurrent;
	}
}
