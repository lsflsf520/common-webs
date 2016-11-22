package com.yisi.stiku.quartz.web.model;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefinitionManager {

	protected static final transient Log LOG = LogFactory
			.getLog(DefinitionManager.class);
	private Map definitionMap;

	public DefinitionManager() {
		definitionMap = new TreeMap();
	}

	public void addDefinition(String name, JobDefinition def) {
		LOG.debug((new StringBuilder(
				"\u7A0B\u5E8F\u8FD0\u884C\u5728-addDefinition()\u91CC.name="))
				.append(name).append(";def=").append(def.toString()).toString());
		definitionMap.put(name, def);
	}

	public void removeDefinition(String name) {
		if (definitionMap.containsKey(name))
			definitionMap.remove(name);
	}

	public JobDefinition getDefinition(String jobName) {
		LOG.debug((new StringBuilder(
				"\u7A0B\u5E8F\u8FD0\u884C\u5728-getDefinition()\u91CC.definitionMap="))
				.append(definitionMap.toString()).toString());
		return (JobDefinition) definitionMap.get(jobName);
	}

	public Map getDefinitions() {
		return definitionMap;
	}

}
