package com.yisi.stiku.quartz.web.vo;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class JobDetailForm {

	public static String FORM_NAME = "jobDetailForm";
	@Pattern(message = "任务名必须为英文字符、数字", regexp = "^[A-Za-z0-9\\-]+$")
	protected String name;
	@Pattern(message = "组名必须为英文字符、数字", regexp = "^[A-Za-z0-9\\-]+$")
	protected String groupName;
	@Length(max = 250, message = "长度不得大于250")
	protected String description;

	protected String jobClass;
	boolean volatility;
	boolean durability;
	boolean stateful;
	boolean recoveryRequesting;
	protected String saveAction;
	protected String cancelAction;
	protected String deleteAction;

	public boolean isVolatility() {
		return volatility;
	}

	public void setVolatility(boolean volatility) {
		this.volatility = volatility;
	}

	protected String editAction;
	protected String scheduleSimpleTriggerAction;
	protected String scheduleCronTriggerAction;
	protected String scheduleUICronTriggerAction;
	protected String executeJobAction;
	protected String unscheduleAction;
	protected String unscheduleTriggerName;
	protected String unscheduleTriggerGroup;
	private List<TriggerForm> triggers;
	private List<ValueForm> values;
	private List<ListenerDTO> jobListeners;
	String parameterNames[];
	String parameterValues[];

	public String[] getParameterNames() {
		return parameterNames;
	}

	public void setParameterNames(String[] parameterNames) {
		this.parameterNames = parameterNames;
	}

	public String[] getParameterValues() {
		return parameterValues;
	}

	public void setParameterValues(String[] parameterValues) {
		this.parameterValues = parameterValues;
	}

	public JobDetailForm() {
		durability = true;
		values = new ArrayList<ValueForm>();
		triggers = new ArrayList<TriggerForm>();
		jobListeners = new ArrayList<ListenerDTO>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public boolean isVolatile() {
		return volatility;
	}

	public void setVolatile(boolean volatility) {
		this.volatility = volatility;
	}

	public boolean isRecoveryRequesting() {
		return recoveryRequesting;
	}

	public void setRecoveryRequesting(boolean recover) {
		recoveryRequesting = recover;
	}

	public String getSaveAction() {
		return saveAction;
	}

	public void setSaveAction(String saveAction) {
		this.saveAction = saveAction;
	}

	public String getCancelAction() {
		return cancelAction;
	}

	public void setCancelAction(String cancelAction) {
		this.cancelAction = cancelAction;
	}

	public String getScheduleCronTriggerAction() {
		return scheduleCronTriggerAction;
	}

	public String getScheduleSimpleTriggerAction() {
		return scheduleSimpleTriggerAction;
	}

	public String getUnscheduleAction() {
		return unscheduleAction;
	}

	public String getUnscheduleTriggerGroup() {
		return unscheduleTriggerGroup;
	}

	public String getUnscheduleTriggerName() {
		return unscheduleTriggerName;
	}

	public void setScheduleCronTriggerAction(String scheduleCronTriggerAction) {
		this.scheduleCronTriggerAction = scheduleCronTriggerAction;
	}

	public void setScheduleSimpleTriggerAction(
			String scheduleSimpleTriggerAction) {
		this.scheduleSimpleTriggerAction = scheduleSimpleTriggerAction;
	}

	public void setUnscheduleAction(String unscheduleAction) {
		this.unscheduleAction = unscheduleAction;
	}

	public void setUnscheduleTriggerGroup(String unscheduleTriggerGroup) {
		this.unscheduleTriggerGroup = unscheduleTriggerGroup;
	}

	public void setUnscheduleTriggerName(String unscheduleTriggerName) {
		this.unscheduleTriggerName = unscheduleTriggerName;
	}

	public String getDeleteAction() {
		return deleteAction;
	}

	public void setDeleteAction(String deleteAction) {
		this.deleteAction = deleteAction;
	}

	public String getEditAction() {
		return editAction;
	}

	public String getExecuteJobAction() {
		return executeJobAction;
	}

	public void setEditAction(String editAction) {
		this.editAction = editAction;
	}

	public void setExecuteJobAction(String executeJobAction) {
		this.executeJobAction = executeJobAction;
	}

	public void setTrigger(int i, TriggerForm form) {
		triggers.set(i, form);
	}

	public TriggerForm getTrigger(int i) {
		for (; i >= triggers.size(); triggers.add(new TriggerForm()))
			;
		return (TriggerForm) triggers.get(i);
	}

	public void setValue(int i, ValueForm form) {
		values.set(i, form);
	}

	public ValueForm getValue(int i) {
		for (; i >= values.size(); values.add(new ValueForm()))
			;
		return (ValueForm) values.get(i);
	}

	public List<TriggerForm> getTriggers() {
		return triggers;
	}

	public void setTriggers(List<TriggerForm> triggers) {
		this.triggers = triggers;
	}

	public List<ValueForm> getValues() {
		return values;
	}

	public void setValues(List<ValueForm> values) {
		this.values = values;
	}

	public List<ListenerDTO> getJobListeners() {
		return jobListeners;
	}

	public void setJobListeners(List<ListenerDTO> jobListeners) {
		this.jobListeners = jobListeners;
	}

	public void setJobListener(int i, ListenerDTO form) {
		jobListeners.set(i, form);
	}

	public ListenerDTO getJobListener(int i) {
		for (; i >= jobListeners.size(); jobListeners.add(new ListenerDTO()))
			;
		return (ListenerDTO) jobListeners.get(i);
	}

	public boolean isStateful() {
		return stateful;
	}

	public boolean isDurability() {
		return durability;
	}

	public void setDurability(boolean durability) {
		this.durability = durability;
	}

	public void setStateful(boolean stateful) {
		this.stateful = stateful;
	}

	public String getScheduleUICronTriggerAction() {
		return scheduleUICronTriggerAction;
	}

	public void setScheduleUICronTriggerAction(String string) {
		scheduleUICronTriggerAction = string;
	}

}
