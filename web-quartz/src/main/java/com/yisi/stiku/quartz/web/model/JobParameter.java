// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst 
// Source File Name:   JobParameter.java

package com.yisi.stiku.quartz.web.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JobParameter {

	private String name;
	private String description;
	private boolean required;
	private String inputMask;
	private static final Log LOG = LogFactory.getLog(JobParameter.class);

	public JobParameter() {
		LOG.debug((new StringBuilder("\u7A0B\u5E8F\u8FD0\u884C\u5728"))
				.append(toString()).append("-JobParameter ()\u91CC").toString());
	}

	public JobParameter(String name, String desciption, String inputMask) {
		this.name = name;
		description = desciption;
		this.inputMask = inputMask;
	}

	public JobParameter(String name, String desciption, boolean required,
			String inputMask) {
		this.name = name;
		description = desciption;
		this.required = required;
		this.inputMask = inputMask;
	}

	public String getDescription() {
		LOG.debug((new StringBuilder("\u7A0B\u5E8F\u8FD0\u884C\u5728"))
				.append(toString())
				.append("-getDescription()\u91CCdescription=")
				.append(description).toString());
		return description;
	}

	public String getInputMask() {
		return inputMask;
	}

	public String getName() {
		return name;
	}

	public boolean isRequired() {
		return required;
	}

	public void setDescription(String string) {
		LOG.debug((new StringBuilder("\u7A0B\u5E8F\u8FD0\u884C\u5728"))
				.append(toString())
				.append("-setDescription()\u91CCdescription=")
				.append(description).toString());
		description = string;
	}

	public void setInputMask(String string) {
		inputMask = string;
	}

	public void setName(String string) {
		name = string;
	}

	public void setRequired(boolean b) {
		required = b;
	}

}
