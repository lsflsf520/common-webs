package com.yisi.stiku.quartz.web.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class TriggerForm {

	public static final String DATE_FORMAT_PATTERN = "yy-MM-dd HH:mm:ss";
	SimpleDateFormat dateFormatter;
	public static final String START_TIME_PROP = "startTime";
	public static final String STOP_TIME_PROP = "stopTime";
	public static final String VOLATILITY_PROP = "volatility";
	public static final String MISFIRE_INSTRUCTION_PROP = "misFireInstruction";
	public static final String TRIGGER_NAME_PROP = "triggerName";
	public static final String TRIGGER_GROUP_PROP = "triggerGroup";
	public static final String DESCRIPTION_PROP = "description";
	public static final String JOB_NAME_PROP = "jobName";
	public static final String JOB_GROUP_PROP = "jobGroup";
	public static final String SCHEDULE_ACTION_PROP = "scheduleAction";
	public static final String CANCEL_ACTION_PROP = "cancelAction";
	protected String startTime;
	protected String stopTime;
	private boolean volatility;
	private int misFireInstruction;
	private String triggerName;
	private String triggerGroup;
	private String jobName;
	private String jobGroup;
	private String description;
	private String nextFireTime;
	private String previousFireTime;
	private String type;
	private String scheduleAction;
	private String cancelAction;
	private String triggerType;
	private String cronExpression;

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	// 重复次数
	private Integer repeatCount;

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	public Integer getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(Integer repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	// 重复间隔
	private Integer repeatInterval;

	public TriggerForm() {
		dateFormatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
	}

	public String getStartTime() {
		return startTime;
	}

	public Date getStartTimeAsDate() throws ParseException {
		return StringUtils.isNotBlank(startTime) ? dateFormatter.parse(startTime) : new Date();

	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStopTime() {
		return stopTime;
	}

	public Date getStopTimeAsDate() throws ParseException {
		return StringUtils.isNotBlank(stopTime) ? dateFormatter.parse(stopTime)
				: dateFormatter.parse("2099-12-12 00:00:00");

	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public String getScheduleAction() {
		return scheduleAction;
	}

	public void setScheduleAction(String scheduleAction) {
		this.scheduleAction = scheduleAction;
	}

	public String getCancelAction() {
		return cancelAction;
	}

	public void setCancelAction(String cancelAction) {
		this.cancelAction = cancelAction;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isVolatility() {
		return volatility;
	}

	public void setVolatility(boolean volatility) {
		this.volatility = volatility;
	}

	public int getMisFireInstruction() {
		return misFireInstruction;
	}

	public void setMisFireInstruction(int misFireInstruction) {
		this.misFireInstruction = misFireInstruction;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String name) {
		triggerName = name;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String group) {
		triggerGroup = group;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getNextFireTime() {
		return nextFireTime;
	}

	public void setNextFireTime(String nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	public String getPreviousFireTime() {
		return previousFireTime;
	}

	public void setPreviousFireTime(String previousFireTime) {
		this.previousFireTime = previousFireTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
