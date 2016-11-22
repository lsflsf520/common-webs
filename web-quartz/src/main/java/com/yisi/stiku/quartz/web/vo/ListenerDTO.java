// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst 
// Source File Name:   ListenerDTO.java

package com.yisi.stiku.quartz.web.vo;

public class ListenerDTO {

	public static String SCHEDULER_LISTENER = "schedulerListener";
	public static String GLOBAL_JOB_LISTENER = "globalJobListener";
	public static String GLOBAL_TRIGGER_LISTENER = "globalTriggerListener";
	public static String REGISTERED_JOB_LISTENER = "registeredJobListener";
	public static String REGISTERED_TRIGGER_LISTENER = "registeredTriggerListener";
	private String listenerType;
	private String listenerClass;
	private String name;
	private boolean selected;

	public ListenerDTO() {
	}

	public String getListenerClass() {
		return listenerClass;
	}

	public void setListenerClass(String listenerClass) {
		this.listenerClass = listenerClass;
	}

	public String getName() {
		return name;
	}

	public void setName(String listenerName) {
		name = listenerName;
	}

	public String getListenerType() {
		return listenerType;
	}

	public void setListenerType(String listenerType) {
		this.listenerType = listenerType;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
