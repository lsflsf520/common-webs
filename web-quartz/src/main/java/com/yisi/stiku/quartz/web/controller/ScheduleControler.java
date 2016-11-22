package com.yisi.stiku.quartz.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yisi.stiku.quartz.web.vo.ChooseSchedulerForm;
import com.yisi.stiku.quartz.web.vo.JobDetailForm;
import com.yisi.stiku.quartz.web.vo.ListenerDTO;
import com.yisi.stiku.quartz.web.vo.SchedulerDTO;

/**
 * Process scheduler command, and populate schedule summary information
 * 
 */
@Controller
@RequestMapping("/schedule")
public class ScheduleControler extends ScheduleBase {
	private static final Logger LOG = LoggerFactory
			.getLogger(ScheduleControler.class);

	private static final long serialVersionUID = 5786951823109350172L;

	@RequestMapping("/chooseSchedule")
	public String chooseSchedule(HttpServletRequest request,
			HttpServletResponse response) {
		ChooseSchedulerForm scheduleInfo = new ChooseSchedulerForm();
		String schedulerName = null;
		String command = request.getParameter("command");
		schedulerName = request.getParameter("schedulerName");
		Scheduler choosenScheduler = getCurrentScheduler(schedulerName);

		LOG.debug("程序运行在" + this + ";choosenScheduler=" + choosenScheduler);
		try {
			LOG.debug("程序运行在execute()choosenScheduler.getSchedulerName()="
					+ choosenScheduler.getSchedulerName());
			if (!(command == null)) {
				if (command.equals("start")) {
					if (choosenScheduler.isShutdown()) {
						choosenScheduler = createSchedulerAndUpdateApplicationContext(schedulerName);
					}
					choosenScheduler.start();
				} else if (command.equals("stop")) {
					choosenScheduler.shutdown();
					// choosenScheduler =
					// StdSchedulerFactory.getDefaultScheduler();
				} else if (command.equals("pause")) {
					choosenScheduler.standby();
				} else if (command.equals("waitAndStopScheduler")) {
					choosenScheduler.shutdown(true);
				} else if (command.equals("pauseAll")) {
					choosenScheduler.pauseAll();
				} else if (command.equals("resumeAll")) {
					choosenScheduler.resumeAll();
				}
			}
			LOG.debug("程序运行在" + this + ";choosenScheduler=" + choosenScheduler
					+ ";scheduleInfo=" + scheduleInfo);
			this.populateSchedulerForm(choosenScheduler, scheduleInfo);

		} catch (SchedulerException e) {
			LOG.error(
					"SchedulerException error in Scheduler Controller,  command=:"
							+ command, e);
		} catch (Exception e) {
			LOG.error("Exception error in Scheduler Controller,  command=:"
					+ command, e);
		}
		request.setAttribute("chooseSchedulerForm", choosenScheduler);
		request.setAttribute("scheduleInfo", scheduleInfo);

		return "/quartz/chooseScheduler";
	}

	/**
	 * populate DTO with scheduler information summary.
	 * 
	 * @param choosenScheduler
	 * @param form
	 * @throws Exception
	 */
	private void populateSchedulerForm(Scheduler choosenScheduler,
			ChooseSchedulerForm form) throws Exception {

		Collection scheduleCollection = new StdSchedulerFactory()
				.getAllSchedulers();
		Iterator itr = scheduleCollection.iterator();

		form.setSchedulers(new ArrayList());

		try {
			form.setChoosenSchedulerName(choosenScheduler.getSchedulerName());

			while (itr.hasNext()) {
				Scheduler scheduler = (Scheduler) itr.next();
				form.getSchedulers().add(scheduler);
			}

		} catch (SchedulerException e) {
			throw new Exception(e);
		}

		SchedulerDTO schedForm = new SchedulerDTO();
		schedForm.setSchedulerName(choosenScheduler.getSchedulerName());
		schedForm.setNumJobsExecuted(String.valueOf(choosenScheduler
				.getMetaData().getNumberOfJobsExecuted()));

		if (choosenScheduler.getMetaData().isJobStoreSupportsPersistence()) {
			schedForm
					.setPersistenceType("value.scheduler.persiststenceType.database");
		} else {
			schedForm
					.setPersistenceType("value.scheduler.persiststenceType.memory"); // mp
																						// possible
																						// bugfix
		}
		schedForm.setRunningSince(String.valueOf(choosenScheduler.getMetaData()
				.getRunningSince()));
		if (choosenScheduler.isShutdown()) {
			schedForm.setState("value.scheduler.state.stopped");
		} else if (choosenScheduler.isInStandbyMode()) {
			schedForm.setState("value.scheduler.state.paused");
		} else {
			schedForm.setState("value.scheduler.state.started");
		}

		schedForm.setThreadPoolSize(String.valueOf(choosenScheduler
				.getMetaData().getThreadPoolSize()));
		schedForm.setVersion(choosenScheduler.getMetaData().getVersion());
		schedForm.setSummary(choosenScheduler.getMetaData().getSummary());

		List jobDetails = choosenScheduler.getCurrentlyExecutingJobs();
		LOG.debug("程序运行在populateSchedulerForm()jobDetails=" + jobDetails);
		LOG.debug("程序运行在populateSchedulerForm()-jobDetails.iterator().hasNext()="
				+ jobDetails.iterator().hasNext());
		form.setExecutingJobs(new ArrayList());// 修正bug【scheduleControl页面当前执行的Job(Currently
												// executing
												// jobs)显示问题，以及新建JOB后异常问题】 by
												// gsp 20110407
		for (Iterator iter = jobDetails.iterator(); iter.hasNext();) {

			JobExecutionContext job = (JobExecutionContext) iter.next();
			JobDetail jobDetail = job.getJobDetail();

			JobDetailForm jobForm = new JobDetailForm();
			jobForm.setGroupName(jobDetail.getKey().getGroup());
			jobForm.setName(jobDetail.getKey().getName());
			jobForm.setDescription(jobDetail.getDescription());
			jobForm.setJobClass(jobDetail.getJobClass().getName());
			LOG.debug("程序运行在populateSchedulerForm()jobForm=" + jobForm);
			LOG.debug("程序运行在populateSchedulerForm()form=" + form);
			LOG.debug("程序运行在populateSchedulerForm()form.getExecutingJobs()="
					+ form.getExecutingJobs());
			form.getExecutingJobs().add(jobForm);
		}
		List<String> calendars = choosenScheduler.getCalendarNames();

		List<JobListener> jobListeners = choosenScheduler.getListenerManager()
				.getJobListeners();
		for (Iterator iter = jobListeners.iterator(); iter.hasNext();) {
			JobListener jobListener = (JobListener) iter.next();
			ListenerDTO listenerForm = new ListenerDTO();
			listenerForm.setName(jobListener.getName());
			listenerForm.setListenerClass(jobListener.getClass().getName());
			schedForm.getGlobalJobListeners().add(listenerForm);
		}

		form.setScheduler(schedForm);

	}

}
