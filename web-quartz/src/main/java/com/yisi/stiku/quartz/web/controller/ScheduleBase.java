package com.yisi.stiku.quartz.web.controller;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class ScheduleBase extends BaseController {
	protected static final transient Log LOG = LogFactory
			.getLog(ScheduleBase.class);
	private static final long serialVersionUID = 0xdf823dfe1fa680e1L;
	String jobName;
	String jobGroup;
	protected String triggerGroup;
	protected String description;
	protected String triggerName;
	protected String startTime;
	protected Date startTimeAsDate;
	protected String stopTime;
	protected Date stopTimeAsDate;
	public static final String CURRENT_SCHEDULER_PROP = "currentScheduler";

	public ScheduleBase() {
		jobName = "";
		jobGroup = "";
		triggerGroup = "";
		description = new String();
		triggerName = new String();
		startTime = new String();
		startTimeAsDate = new Date();
		stopTime = new String();
		stopTimeAsDate = new Date();
	}

	public static Scheduler createSchedulerAndUpdateApplicationContext(
			String schedulerName) {
		Scheduler currentScheduler = null;
		try {
			if (schedulerName != null && schedulerName.length() > 0)
				currentScheduler = (new StdSchedulerFactory())
						.getScheduler(schedulerName);
			else
				currentScheduler = StdSchedulerFactory.getDefaultScheduler();
			getQuartzContext().put("currentScheduler", currentScheduler);

		} catch (SchedulerException e) {
			LOG.error("Problem creating scheduler", e);
		}
		return currentScheduler;
	}

	public static Scheduler getCurrentScheduler(String schedulerName) {
		Scheduler currentScheduler = (Scheduler) getQuartzContext().get(
				"currentScheduler");
		if (currentScheduler == null)
			currentScheduler = createSchedulerAndUpdateApplicationContext(schedulerName);
		return currentScheduler;
	}

	public static Scheduler getCurrentScheduler() {
		return getCurrentScheduler(null);
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobGroup(String string) {
		jobGroup = string;
	}

	public void setJobName(String string) {
		jobName = string;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Date getStartTimeAsDate() {
		return startTimeAsDate;
	}

	public void setStartTimeAsDate(Date startTimeAsDate) {
		this.startTimeAsDate = startTimeAsDate;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public Date getStopTimeAsDate() {
		return stopTimeAsDate;
	}

	public void setStopTimeAsDate(Date stopTimeAsDate) {
		this.stopTimeAsDate = stopTimeAsDate;
	}
}
