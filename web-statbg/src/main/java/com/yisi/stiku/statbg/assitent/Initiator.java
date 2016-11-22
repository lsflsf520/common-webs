package com.yisi.stiku.statbg.assitent;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.yisi.stiku.statbg.func.Domain2Project;
import com.yisi.stiku.statbg.func.FuncContext;

/**
 * @author shangfeng
 *
 */
@Component
public class Initiator implements ApplicationContextAware, InitializingBean {

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

	}

	@Override
	public void afterPropertiesSet() throws Exception {

		FuncContext.registerFunc("dm2proj", new Domain2Project());

	}

}
