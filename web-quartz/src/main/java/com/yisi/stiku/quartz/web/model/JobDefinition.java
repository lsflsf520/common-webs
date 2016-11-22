package com.yisi.stiku.quartz.web.model;

import java.util.ArrayList;
import java.util.List;

public class JobDefinition {

	private String name;
	private String description;
	private String className;
	private final List<JobParameter> parameters;

	public JobDefinition() {
		parameters = new ArrayList<JobParameter>();
	}

	public JobDefinition(String jobName, String desc, String cName) {
		parameters = new ArrayList<JobParameter>();
		name = jobName;
		description = desc;
		className = cName;
	}

	public String getClassName() {
		return className;
	}

	public String getDescription() {
		return description;
	}

	public List<JobParameter> getParameters() {
		return parameters;
	}

	public void setClassName(String string) {
		className = string;
	}

	public void setDescription(String string) {
		description = string;
	}

	public void addParameter(JobParameter param) {
		parameters.add(param);
	}

	public String getName() {
		return name;
	}

	public void setName(String string) {
		name = string;
	}
}
