package com.yisi.stiku.quartz.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TriggerHelperl {

	private static final Logger logger = LoggerFactory
			.getLogger(TriggerHelperl.class);

	public TriggerHelperl() {
	}

	public static String getTriggerType(Trigger trigger) {
		String type = null;
		if (trigger == null)
			return null;
		if (trigger.getClass() == org.quartz.SimpleTrigger.class)
			type = "simple";
		else if (trigger.getClass() == org.quartz.CronTrigger.class)
			type = "cron";
		else
			type = trigger.getClass().getName();
		return type;
	}

	public static Trigger[] getTriggersFromJob(Scheduler scheduler,
			String jobName, String jobGroup) throws ServletException {
		List<Trigger> triggerList = new ArrayList<Trigger>(5);
		List<String> groups = null;
		try {
			groups = scheduler.getTriggerGroupNames();
		} catch (SchedulerException e) {
			logger.error("When getting all trigger groups", e);
			throw new ServletException("When getting all trigger groups", e);
		}

		int j = groups.size();
		for (int i = 0; i < j; i++) {
			String group = groups.get(i);
			List<String> triggerNames = new ArrayList<String>();
			try {
				for (TriggerKey key : scheduler.getTriggerKeys(GroupMatcher
						.triggerGroupEquals(group))) {
					triggerNames.add(key.getName());
				}

				// names = scheduler.getTriggerNames(group);
			} catch (SchedulerException e) {
				logger.error(
						(new StringBuilder(
								"When getting all trigger in group groups "))
								.append(group).toString(), e);
				throw new ServletException((new StringBuilder(
						"When getting all trigger in group groups ")).append(
						group).toString(), e);
			}

			int l = triggerNames.size();
			for (int k = 0; k < l; k++) {
				String name = triggerNames.get(k);
				Trigger trigger = null;
				try {
					TriggerKey tk = new TriggerKey(name, group);
					trigger = scheduler.getTrigger(tk);
				} catch (SchedulerException e) {
					logger.error((new StringBuilder("When getting trigger "))
							.append(name).append(" in group ").append(group)
							.toString(), e);
					throw new ServletException((new StringBuilder(
							"When getting trigger ")).append(name)
							.append(" in group ").append(group).toString(), e);
				}
				if (trigger == null)
					logger.warn((new StringBuilder("The trigger "))
							.append(name).append(" in group ").append(group)
							.append(" was null").toString());

				else if (trigger.getJobKey().getName().equals(jobName)
						&& trigger.getJobKey().getGroup().equals(jobGroup))
					triggerList.add(trigger);
			}

		}

		Trigger retArr[] = new Trigger[triggerList.size()];
		triggerList.toArray(retArr);
		return retArr;
	}

}
