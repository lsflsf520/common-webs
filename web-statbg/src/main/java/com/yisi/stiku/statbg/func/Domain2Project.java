package com.yisi.stiku.statbg.func;

import java.util.List;
import java.util.Map;

import com.yisi.stiku.common.utils.RegexUtil;
import com.yisi.stiku.conf.ConfigOnZk;
import com.yisi.stiku.conf.ZkConstant;

/**
 * @author shangfeng
 *
 */
public class Domain2Project implements Func<String> {

	@Override
	public String convert(Object value, Map<String, String> funcParamMap) {

		if (value instanceof String) {
			String prefix = "www";
			List<String> valList = RegexUtil.extractGroups("^(\\w+)(-\\w+)?\\.\\w+\\.(com|cn)$", (String) value);
			if (valList != null && !valList.isEmpty()) {
				prefix = valList.get(0);
			}

			return ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, prefix + ".project.name");
		}

		return "UNKNOWN";
	}

}
