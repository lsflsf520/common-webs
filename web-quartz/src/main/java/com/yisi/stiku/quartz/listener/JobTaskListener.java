package com.yisi.stiku.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

@Component
public class JobTaskListener implements JobListener {

	private final String NAME = "JOBTASKLISTENER";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		System.out.println("监听器被执行jobToBeExecuted");

	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		System.out.println("监听器被执行jobExecutionVetoed");

	}

	@Override
	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		System.out.println("监听器被执行jobWasExecuted");

	}

}
