package com.yisi.stiku.quartz.listener;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.listeners.SchedulerListenerSupport;

public class SchedulerListener extends SchedulerListenerSupport {

	@Override
	public void jobAdded(JobDetail jobDetail) {
		// 新增任务时，触发
		super.jobAdded(jobDetail);
	}

	@Override
	public void jobDeleted(JobKey jobKey) {

		super.jobDeleted(jobKey);
	}

}
