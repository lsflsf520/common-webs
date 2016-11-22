package com.yisi.stiku.quartz.web.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yisi.stiku.quartz.web.vo.TriggerForm;
import com.yisi.stiku.quartz.web.vo.TriggerVO;

@Controller
@RequestMapping("/trigger")
public class TriggerController {
	private static final Logger LOG = LoggerFactory.getLogger(TriggerController.class);

	@RequestMapping("/list")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		Scheduler scheduler = ScheduleBase.getCurrentScheduler();
		List<TriggerVO> triggerList = new ArrayList<TriggerVO>();

		try {
			List<String> triggerGroups = scheduler.getTriggerGroupNames();

			for (String groupName : triggerGroups) {

				Set<TriggerKey> triggerNames = scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(groupName));

				for (TriggerKey triggerKey : triggerNames) {
					org.quartz.Trigger trigger = scheduler.getTrigger(triggerKey);
					TriggerVO vo = new TriggerVO();
					TriggerState state = scheduler.getTriggerState(trigger.getKey());

					vo.setTrigger(trigger);
					vo.setState(state);

					triggerList.add(vo);
				}
			}

			request.setAttribute("triggerList", triggerList);
		} catch (SchedulerException e) {
			LOG.error("Problem listing triggers, schedule may be paused or stopped", e, new String[0]);
		}
		return "/quartz/triggerList";
	}

	@RequestMapping("/changeState")
	public String changeState(HttpServletRequest request, HttpServletResponse response) {
		Scheduler scheduler = ScheduleBase.getCurrentScheduler();
		String action = request.getParameter("action");
		String triggerName = request.getParameter("triggerName");
		String triggerGroup = request.getParameter("triggerGroup");
		try {
			if ("NORMAL".equals(action)) {
				scheduler.resumeTrigger(new TriggerKey(triggerName, triggerGroup));
			}

			else if ("PAUSED".equals(action)) {

				scheduler.pauseTrigger(new TriggerKey(triggerName, triggerGroup));
			}
		} catch (SchedulerException e) {

		}

		return "redirect:/trigger/list";
	}

	@RequestMapping("/inputTrigger")
	public String inputTrigger(HttpServletRequest request, HttpServletResponse response) {

		String jobName = request.getParameter("jobName");
		String jobGroup = request.getParameter("jobGroup");
		JobKey key = new JobKey(jobName, jobGroup);

		try {
			JobDetail jobdetail = ScheduleBase.getCurrentScheduler().getJobDetail(key);
			request.setAttribute("jobDetail", jobdetail);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return "";
		}

		return "quartz/createTrigger";
	}

	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, HttpServletResponse response) {

		String triggerName = request.getParameter("triggerName");
		String triggerGroup = request.getParameter("triggerGroup");

		try {

			ScheduleBase.getCurrentScheduler().unscheduleJob(new TriggerKey(triggerName, triggerGroup));
		} catch (SchedulerException e) {
			e.printStackTrace();
			return "";
		}

		return "redirect:/trigger/list";
	}

	@RequestMapping("/create")
	public String create(HttpServletRequest request, HttpServletResponse response, @Valid TriggerForm triggerForm) {
		String startTime = triggerForm.getStartTime();
		String stopTime = triggerForm.getStopTime();
		Integer repeatCount = triggerForm.getRepeatCount();
		Integer repeatInterval = triggerForm.getRepeatInterval();

		boolean repeatCountHasValue = repeatCount != null;
		boolean repeatIntervalHasValue = repeatInterval != null;
		if (triggerForm.getTriggerType().equals("simpl")) {

			try {
				JobDetail detail = ScheduleBase.getCurrentScheduler()
						.getJobDetail(new JobKey(triggerForm.getJobName(), triggerForm.getJobGroup()));

				TriggerKey key = new TriggerKey(triggerForm.getTriggerName(), triggerForm.getTriggerGroup());
				SimpleTrigger simpleTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
						.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(repeatInterval)
								.withRepeatCount(repeatCount))
						.withIdentity(key).startAt(triggerForm.getStartTimeAsDate())
						.endAt(triggerForm.getStopTimeAsDate()).forJob(detail)
						.withDescription(triggerForm.getDescription()).build();

				ScheduleBase.getCurrentScheduler().scheduleJob(simpleTrigger);
				// simpleTrigger.setVolatility(false);
			} catch (SchedulerException e) {

				return "error";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (triggerForm.getTriggerType().equals("cron")) {
			JobDetail detail;
			try {
				detail = ScheduleBase.getCurrentScheduler()
						.getJobDetail(new JobKey(triggerForm.getJobName(), triggerForm.getJobGroup()));

				TriggerKey key = new TriggerKey(triggerForm.getTriggerName(), triggerForm.getTriggerGroup());
				CronTrigger simpleTrigger = (CronTrigger) TriggerBuilder.newTrigger()
						.withSchedule(CronScheduleBuilder.cronSchedule(triggerForm.getCronExpression()))
						.withIdentity(key).startAt(triggerForm.getStartTimeAsDate())
						.endAt(triggerForm.getStopTimeAsDate()).forJob(detail)
						.withDescription(triggerForm.getDescription()).build();

				ScheduleBase.getCurrentScheduler().scheduleJob(simpleTrigger);
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return "redirect:/Job/viewJob?jobName=" + triggerForm.getJobName() + "&jobGroup=" + triggerForm.getJobGroup();
	}
}
