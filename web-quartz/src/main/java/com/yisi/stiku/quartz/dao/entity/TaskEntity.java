package com.yisi.stiku.quartz.dao.entity;

import java.io.Serializable;

import com.yisi.stiku.common.bean.BaseEntity;

public class TaskEntity extends BaseEntity<Integer> implements Serializable {

	private static final long serialVersionUID = 3768773456541518060L;
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private TaskPK taskPK;

	@Override
	public Integer getPK() {
		return id;
	}

	private TaskInfo info;
	private TaskResult result;

	public TaskPK getTaskPK() {
		return taskPK;
	}

	public void setTaskPK(TaskPK taskPK) {
		this.taskPK = taskPK;
	}

	public TaskInfo getInfo() {
		return info;
	}

	public void setInfo(TaskInfo info) {
		this.info = info;
	}

	public TaskResult getResult() {
		return result;
	}

	public void setResult(TaskResult result) {
		this.result = result;
	}

}
