package com.yisi.stiku.quartz.web.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yisi.stiku.quartz.util.FormatUtil;
import com.yisi.stiku.quartz.util.StringUtil;
import com.yisi.stiku.quartz.web.TriggerHelperl;
import com.yisi.stiku.quartz.web.common.ErrorUtils;
import com.yisi.stiku.quartz.web.model.JobDefinition;
import com.yisi.stiku.quartz.web.model.JobParameter;
import com.yisi.stiku.quartz.web.vo.JobDetailForm;
import com.yisi.stiku.quartz.web.vo.ListenerDTO;
import com.yisi.stiku.quartz.web.vo.TriggerForm;
import com.yisi.stiku.quartz.web.vo.TriggerVO;
import com.yisi.stiku.web.util.WebUtils;

@Controller
@RequestMapping("/Job")
public class JobController {
	private static final Logger LOG = LoggerFactory.getLogger(JobController.class);
	private static final long serialVersionUID = 0x25019ff3c0b79ba8L;

	private List<JobDetail> jobList;

	JobDefinition jobDefinition;
	String definitionName;

	public JobController() {
		jobDefinition = new JobDefinition();
		definitionName = "";
	}

	@RequestMapping("/start")
	public String start(HttpServletRequest request, HttpServletResponse response) {
		definitionName = request.getParameter("definitionName");
		// ((JobDetailImpl) jobDetail).setJobDataMap(new JobDataMap());
		if (definitionName.length() > 0)
			jobDefinition = BaseController.getDefinitionManager().getDefinition(definitionName);
		if (jobDefinition != null) {
			request.setAttribute("jobDefinition", jobDefinition);

			return "/quartz/createDefinedJob";
		} else {
			LOG.error("Job定义没有找到");
			return "/quartz/createDefinedJob";
		}

	}

	private boolean matchesSearch(String jobName, String searchName) {
		if (StringUtil.isEmpty(searchName))
			return true;
		else
			return jobName.contains(searchName);
	}

	@RequestMapping("/jobList")
	public String jobList(HttpServletRequest request, HttpServletResponse response) {
		String searchName = request.getParameter("searchName");
		Scheduler scheduler = ScheduleBase.getCurrentScheduler();
		jobList = new ArrayList<JobDetail>();
		try {
			List<String> jobGroups = scheduler.getJobGroupNames();
			List<String> addedJobs = new ArrayList<String>(jobGroups.size());

			int j = jobGroups.size();
			for (int i = 0; i < j; i++) {
				String groupName = jobGroups.get(i);
				Set<JobKey> jobs = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
				for (JobKey jobKey : jobs) {
					JobDetail jobDetail = scheduler.getJobDetail(jobKey);
					String key = (new StringBuilder(String.valueOf(jobKey.getName()))).append(groupName).toString();
					if (!addedJobs.contains(key) && matchesSearch(jobDetail.getKey().getName(), searchName)) {
						jobList.add(jobDetail);
						addedJobs.add(key);
					}

				}

			}

		} catch (SchedulerException e) {
			LOG.error("Problem listing jobs, schedule may be paused or stopped", e, new String[0]);
		}
		request.setAttribute("jobList", jobList);
		return "/quartz/jobList";
	}

	@RequestMapping("/viewJob")
	public String viewJob(HttpServletRequest request, HttpServletResponse response) {
		List<TriggerForm> jobTriggers = new ArrayList<TriggerForm>();

		JobDetailForm form = new JobDetailForm();
		String jobName = request.getParameter("jobName");
		String jobGroup = request.getParameter("jobGroup");
		Scheduler scheduler = ScheduleBase.getCurrentScheduler();
		List<TriggerVO> triggerVOs = null;
		JobDetail jobDetail = null;

		try {
			jobDetail = scheduler.getJobDetail(new JobKey(jobName, jobGroup));
			JobKey key = null;
			if (jobDetail.getKey().getName() == null) {
				key = new JobKey(jobName, jobGroup);
				jobDetail = scheduler.getJobDetail(key);
			}

			else {
				jobDetail = scheduler.getJobDetail(jobDetail.getKey());

				LOG.debug((new StringBuilder("\u5728")).append(toString()).append("-view()\u91CCjobDetail=")
						.append(jobDetail).toString());
			}
			List<? extends Trigger> trigger = scheduler
					.getTriggersOfJob(new JobKey(jobDetail.getKey().getName(), jobDetail.getKey().getGroup()));

			triggerVOs = new ArrayList<TriggerVO>(trigger.size());
			for (Trigger coverTrigger : trigger) {
				TriggerVO vo = new TriggerVO();
				vo.setTrigger(coverTrigger);
				TriggerState state = scheduler.getTriggerState(coverTrigger.getKey());

				vo.setState(state);
				triggerVOs.add(vo);

			}

			converViewJob(jobDetail, form, scheduler, jobTriggers);
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		// request.setAttribute("triggers", trigger);
		request.setAttribute("triggers", triggerVOs);
		request.setAttribute("jobDetail", jobDetail);

		return "/quartz/viewJob";

	}

	private void converViewJob(JobDetail jobDetail, JobDetailForm form,

			Scheduler scheduler, List<TriggerForm> jobTriggers) throws ServletException {
		LOG.debug((new StringBuilder("\u5728")).append(toString()).append("-populateForm()\u91CCjobDetail=")
				.append(jobDetail).toString());

		Trigger triggers[] = TriggerHelperl.getTriggersFromJob(scheduler, jobDetail.getKey().getName(),
				jobDetail.getKey().getGroup());
		Trigger atrigger[];
		int j = (atrigger = triggers).length;
		for (int i = 0; i < j; i++) {
			Trigger trigger = atrigger[i];

			TriggerForm tForm = new TriggerForm();
			tForm.setDescription(trigger.getDescription());
			tForm.setJobGroup(trigger.getJobKey().getGroup());
			tForm.setJobName(trigger.getJobKey().getName());
			tForm.setMisFireInstruction(trigger.getMisfireInstruction());
			tForm.setStartTime(FormatUtil.getDateAsString(trigger.getStartTime()));
			tForm.setStopTime(FormatUtil.getDateAsString(trigger.getEndTime()));
			tForm.setTriggerGroup(trigger.getKey().getGroup());
			tForm.setTriggerName(trigger.getKey().getName());
			tForm.setNextFireTime(FormatUtil.getDateAsString(trigger.getNextFireTime()));
			tForm.setPreviousFireTime(FormatUtil.getDateAsString(trigger.getPreviousFireTime()));
			tForm.setType(TriggerHelperl.getTriggerType(trigger));
			jobTriggers.add(tForm);

		}

		try {

			List<JobListener> JobListeners = scheduler.getListenerManager().getJobListeners();

			for (JobListener jobListener : JobListeners) {
				String name = jobListener.getName();
				JobListener jl = scheduler.getListenerManager().getJobListener(name);
				if (jl.getName().equals(name)) {
					ListenerDTO listenerForm = new ListenerDTO();
					listenerForm.setName(jobListener.getName());
					listenerForm.setListenerClass(jobListener.getClass().getName());
					form.getJobListeners().add(listenerForm);
				}
			}

		} catch (SchedulerException e) {
			LOG.error("\u53D1\u751F\u4E86\u5F02\u5E38\uFF1A", e);
		}
	}

	@RequestMapping("/deleteJob")
	public ModelAndView deleteJob(HttpServletRequest request, HttpServletResponse response) {
		String jobName = request.getParameter("jobName");
		String jobGroup = request.getParameter("jobGroup");
		Scheduler scheduler = ScheduleBase.getCurrentScheduler();
		try {
			scheduler.deleteJob(new JobKey(jobName, jobGroup));
		} catch (SchedulerException e) {
			LOG.warn("删除任务失败", e);
			return new ModelAndView();
		}
		return new ModelAndView("redirect:/Job/jobList");
	}

	@RequestMapping("/updatejob")
	public ModelAndView updatejob(HttpServletRequest request, HttpServletResponse response,
			@Valid JobDetailForm jobDetailForm, BindingResult result) {

		String defName = request.getParameter("definitionName");
		if (result.hasErrors()) {
			WebUtils.writeJson(ErrorUtils.buildErrorMessage(result), request, response);
			new ModelAndView("redirect:/Job/jobList");
		}

		label0: {
			LOG.debug((new StringBuilder("\u7A0B\u5E8F\u8FD0\u884C\u5728")).append(toString())
					.append("-execute()\u91CC").toString(), new String[0]);
			if (definitionName.length() > 0)
				jobDefinition = BaseController.getDefinitionManager().getDefinition(definitionName);

			try {
				JobDetail jobDetail = converJobDetail(jobDetailForm);
				if (validateJobData(jobDetail)) {
					boolean replace = true;

					// 只能新增，无法修改之前存在的
					ScheduleBase.getCurrentScheduler().addJob(jobDetail, replace);
					// ScheduleBase.getCurrentScheduler().
					break label0;
				}
			} catch (ClassNotFoundException e) {
				LOG.error("Class Not Found " + e.getMessage());

				// return "/quartz/createDefinedJob";
			} catch (SchedulerException e) {
				LOG.error(e.getMessage());

				// return "/quartz/createDefinedJob";
			}

		}
		return new ModelAndView("redirect:/Job/jobList");

	}

	@RequestMapping("/create")
	public void create(HttpServletRequest request, HttpServletResponse response, @Valid JobDetailForm jobDetailForm,
			BindingResult result) {

		String defName = request.getParameter("definitionName");
		if (result.hasErrors()) {
			WebUtils.writeJson(ErrorUtils.buildErrorMessage(result), request, response);
			return;
		}

		label0: {
			LOG.debug((new StringBuilder("\u7A0B\u5E8F\u8FD0\u884C\u5728")).append(toString())
					.append("-execute()\u91CC").toString(), new String[0]);
			if (definitionName.length() > 0)
				jobDefinition = BaseController.getDefinitionManager().getDefinition(definitionName);

			try {
				JobDetail jobDetail = converJobDetail(jobDetailForm);
				if (validateJobData(jobDetail)) {
					boolean replace = true;

					// 只能新增，无法修改之前存在的
					ScheduleBase.getCurrentScheduler().addJob(jobDetail, replace);

					break label0;
				}
			} catch (ClassNotFoundException e) {
				LOG.error("Class Not Found " + e.getMessage());

				// return "/quartz/createDefinedJob";
			} catch (SchedulerException e) {
				LOG.error(e.getMessage());

				// return "/quartz/createDefinedJob";
			}
			// return "/quartz/createDefinedJob";
		}

		// return "redirect:/Job/jobList";
	}

	private JobDetail converJobDetail(JobDetailForm jobDetailForm) throws ClassNotFoundException {
		JobDetailImpl jobDetail = new JobDetailImpl();
		jobDetail.setJobDataMap(new JobDataMap());
		jobDetail.setKey(new JobKey(jobDetailForm.getName(), jobDetailForm.getGroupName()));

		jobDetail.setDurability(jobDetailForm.isDurability());
		jobDetail.setRequestsRecovery(jobDetailForm.isRecoveryRequesting());
		jobDetail.setGroup(jobDetailForm.getGroupName());
		jobDetail.setDescription(jobDetailForm.getDescription());

		Class jobClass = null;
		jobClass = Class.forName(jobDetailForm.getJobClass());
		((JobDetailImpl) jobDetail).setJobClass(jobClass);
		for (int i = 0; i < jobDetailForm.getParameterNames().length; i++)
			if (jobDetailForm.getParameterNames()[i].trim().length() > 0
					&& jobDetailForm.getParameterValues()[i].trim().length() > 0)
				jobDetail.getJobDataMap().put(jobDetailForm.getParameterNames()[i].trim(),
						jobDetailForm.getParameterValues()[i].trim());

		return jobDetail;
	}

	private boolean validateJobData(JobDetail jobDetail) {
		Iterator itr = jobDefinition.getParameters().iterator();
		Set keys = jobDetail.getJobDataMap().keySet();
		while (itr.hasNext()) {
			JobParameter param = (JobParameter) itr.next();
			if (param.isRequired() && !keys.contains(param.getName())) {
				LOG.error("missing parameter" + param.getName().toString());

				return false;
			}
		}
		return true;
	}

	@RequestMapping("/editJob")
	public String editJob(HttpServletRequest request, HttpServletResponse response) throws SchedulerException {
		JobDetail jobdatail = null;
		String jobName = request.getParameter("jobName");
		String jobGroup = request.getParameter("jobGroup");
		if (jobName.length() > 0 && jobGroup.length() > 0) {

			jobdatail = ScheduleBase.getCurrentScheduler().getJobDetail(new JobKey(jobName, jobGroup));
			request.setAttribute("jobDetail", jobdatail);
			return "/quartz/editJob";
		} else {
			return "error";
		}
	}

	@RequestMapping("/runNow")
	public String runNow(HttpServletRequest request, HttpServletResponse response) {

		String jobName = request.getParameter("jobName");
		String jobGroup = request.getParameter("jobGroup");
		JobKey key = new JobKey(jobName, jobGroup);
		Scheduler scheduler = ScheduleBase.getCurrentScheduler();
		try {

			scheduler.triggerJob(key);
		} catch (Exception e) {
			LOG.error("error executing runNow()\u91CC \u53D1\u751F\u4E86\u5F02\u5E38", e);
			return "error";
		}
		return "redirect:/Job/jobList";
	}

	public JobDefinition getJobDefinition() {
		return jobDefinition;
	}

	public void setJobDefinition(JobDefinition jobDefinition) {
		this.jobDefinition = jobDefinition;
	}

	public String getDefinitionName() {
		return definitionName;
	}

	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}
}
