package com.yisi.stiku.quartz.dao.entity;

public class TaskResult {
	public TaskResult(TaskState state, String resultMessage) {
		super();
		this.state = state;
		this.resultMessage = resultMessage;
	}

	public TaskResult(TaskState state) {
		super();
		this.state = state;

	}

	private TaskState state;
	private String resultMessage;

	public TaskState getState() {
		return state;
	}

	public void setState(TaskState state) {
		this.state = state;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
}
