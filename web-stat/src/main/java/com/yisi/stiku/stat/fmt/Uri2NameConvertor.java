package com.yisi.stiku.stat.fmt;

import org.apache.commons.lang.StringUtils;

import com.yisi.stiku.conf.ConfigOnZk;
import com.yisi.stiku.conf.ZkConstant;

/**
 * @author shangfeng
 *
 */
public class Uri2NameConvertor implements Formatter {

	@Override
	public Object format2Str(Object value) {

		if (value == null || !(value instanceof String)) {
			return value;
		}

		String name = ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, value + ".name");

		return StringUtils.isNotBlank(name) ? name : value;
	}

}
