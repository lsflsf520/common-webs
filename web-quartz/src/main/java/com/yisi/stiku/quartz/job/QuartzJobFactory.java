package com.yisi.stiku.quartz.job;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.scheduling.quartz.SchedulerContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import com.yisi.stiku.quartz.util.SpringUtil;

public class QuartzJobFactory extends SpringBeanJobFactory implements
		SchedulerContextAware {

	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle)
			throws Exception {
		// 调用父类的方法
		Object jobInstance = super.createJobInstance(bundle);
		// 进行注入,这属于Spring的技术,不清楚的可以查看Spring的API.
		SpringUtil.getApplicationContext().getAutowireCapableBeanFactory()
				.autowireBean(jobInstance);
		return jobInstance;
	}

}
