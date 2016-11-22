package com.yisi.stiku.quartz.web.model;

import java.util.HashMap;
import java.util.Map;

public class QuartzContext {
	public static final String QUARTZ_CONTEXT_NAME = "QUARTZ_CONTEXT";
	Map<Object, Object> context = new HashMap<Object, Object>();

	public void setApplication(Map application) {
		put("QUARTZ_CONTEXT", application);
	}

	public Map getApplication() {
		return (Map) get("QUARTZ_CONTEXT");
	}

	public Object get(Object key) {
		return this.context.get(key);
	}

	public void put(Object key, Object value) {
		this.context.put(key, value);
	}
}
