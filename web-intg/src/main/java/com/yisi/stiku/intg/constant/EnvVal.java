package com.yisi.stiku.intg.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shangfeng
 *
 */
public class EnvVal {

	private final static Map<String, Integer> ENV_MAP = new HashMap<String, Integer>();

	static {
		ENV_MAP.put("develop", 2);
		ENV_MAP.put("test", 4);
		ENV_MAP.put("pre", 8);
		ENV_MAP.put("huidu", 16);
		ENV_MAP.put("master", -1);
	}

	public static int getVal(String envName) {

		return ENV_MAP.containsKey(envName) ? ENV_MAP.get(envName) : 0;
	}

	public static boolean contaisEnv(String envName) {

		return ENV_MAP.containsKey(envName);
	}

}
