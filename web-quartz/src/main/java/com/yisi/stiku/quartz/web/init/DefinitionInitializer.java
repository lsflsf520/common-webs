package com.yisi.stiku.quartz.web.init;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.betwixt.io.BeanReader;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.ee.servlet.QuartzInitializerServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yisi.stiku.quartz.listener.JobTaskListener;
import com.yisi.stiku.quartz.listener.QuartzTriggerListener;
import com.yisi.stiku.quartz.web.controller.BaseController;
import com.yisi.stiku.quartz.web.controller.ScheduleBase;
import com.yisi.stiku.quartz.web.model.DefinitionManager;
import com.yisi.stiku.quartz.web.model.QuartzContext;

@SuppressWarnings("deprecation")
@Service
public class DefinitionInitializer extends QuartzInitializerServlet {

	@Autowired
	private QuartzTriggerListener recordListener;

	@Autowired
	private JobTaskListener jobTaskListener;

	public void setRecordListener(QuartzTriggerListener recordListener) {
		this.recordListener = recordListener;
	}

	private static final long serialVersionUID = 0xb95ea1f3cc0040abL;
	public static String DEFAULT_DEFINITION_FILE = "/JobDefinitions.xml";

	public DefinitionInitializer() {
	}

	public void init(ServletConfig cfg) throws ServletException {
		super.init(cfg);
		ServletContext context = cfg.getServletContext();
		String definitionPath = getInitParameter("definition-file");
		BeanReader beanReader = new BeanReader();
		beanReader.getXMLIntrospector().setAttributesForPrimitives(false);
		if (definitionPath != null && definitionPath != "")
			try {
				beanReader.registerBeanClass("JobDefinitions",
						DefinitionManager.class);
				File defFile = new File(definitionPath);
				DefinitionManager defs = null;
				if (!defFile.exists()) {
					log("Alternate user definitions file, not specfic or does not exist.  Default resource /JobDefinitions.xml will be tried.");
					log((new StringBuilder(
							"Attempting to read definitions from file "))
							.append(getClass().getResource(
									DEFAULT_DEFINITION_FILE).getFile())
							.toString());
					URL url = getClass().getResource(DEFAULT_DEFINITION_FILE);
					if (url == null)
						log((new StringBuilder("resource "))
								.append(DEFAULT_DEFINITION_FILE)
								.append(" not found").toString());
					defs = (DefinitionManager) beanReader.parse(url.toURI()
							.toString());
				} else {
					log((new StringBuilder("Reading definitions from "))
							.append(definitionPath).toString());
				}
				if (defs != null) {
					QuartzContext quartzContext = new QuartzContext();
					quartzContext.put(BaseController.JOB_DEFINITIONS_PROP, defs);
					context.setAttribute(QuartzContext.QUARTZ_CONTEXT_NAME,
							quartzContext);

					log((new StringBuilder(String.valueOf(defs.getDefinitions()
							.size()))).append(
							" Definition(s) loaded from config file")
							.toString());
				} else {
					log("no definitions found");
				}
			} catch (IntrospectionException e) {
				log("error reading definitions", e);
			} catch (IOException e) {
				log("IO error reading definitions", e);
			} catch (Exception e) {
				log("error reading definitions", e);
			}
		else
			log("Error definition-file init parameter not specified");

		Scheduler scheduler = ScheduleBase.getCurrentScheduler();

		try {
			if (jobTaskListener == null) {
				jobTaskListener = WebApplicationContextUtils
						.getWebApplicationContext(this.getServletContext())
						.getBean(JobTaskListener.class);
			}
			scheduler.getListenerManager().addJobListener(jobTaskListener);
			// scheduler.getListenerManager().addTriggerListener(recordListener);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

	public void destroy() {
		getServletContext().setAttribute("Util.JOB_DEFINITIONS_PROP", null);
		super.destroy();
	}

}
