package com.yisi.stiku.statbg;

import com.yisi.stiku.statbg.func.Domain2Project;
import com.yisi.stiku.statbg.func.FuncContext;
import com.yisi.stiku.statbg.service.BgStatRpcService;

/**
 * @author shangfeng
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		FuncContext.registerFunc("dm2proj", new Domain2Project());
		new BgStatRpcService().execStat("-configFile context/log/spring-pagetime_log-stat.xml");

	}

}
