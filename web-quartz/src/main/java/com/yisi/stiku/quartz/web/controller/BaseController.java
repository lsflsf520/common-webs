package com.yisi.stiku.quartz.web.controller;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.yisi.stiku.quartz.web.model.DefinitionManager;
import com.yisi.stiku.quartz.web.model.QuartzContext;

public class BaseController {

	public static String JOB_DEFINITIONS_PROP = "definitionManager";

	public BaseController() {
	}

	public static DefinitionManager getDefinitionManager() {

		DefinitionManager manager = null;
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();

		QuartzContext context = (QuartzContext) webApplicationContext
				.getServletContext().getAttribute(
						QuartzContext.QUARTZ_CONTEXT_NAME);
		manager = (DefinitionManager) context.get(JOB_DEFINITIONS_PROP);
		return manager;
	}

	public static QuartzContext getQuartzContext() {

		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();

		QuartzContext context = (QuartzContext) webApplicationContext
				.getServletContext().getAttribute(
						QuartzContext.QUARTZ_CONTEXT_NAME);

		return context;
	}

}
