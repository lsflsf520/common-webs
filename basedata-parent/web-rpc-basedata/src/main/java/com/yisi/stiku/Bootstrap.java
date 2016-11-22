package com.yisi.stiku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Bootstrap {

	public static void main(String[] args) {
//		new ClassPathXmlApplicationContext(new String[]{"context/spring-rpc-service.xml", "context/spring-persist.xml"});
		new ClassPathXmlApplicationContext("classpath*:context/spring-*.xml");
	}

}
