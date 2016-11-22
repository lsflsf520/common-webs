package com.yisi.stiku.stat.fmt;

import com.yisi.stiku.common.utils.NumberUtil;

/**
 * @author shangfeng
 *
 */
public class Sec2MinFormatter implements Formatter {

	@Override
	public Object format2Str(Object value) {

		if (value == null) {
			return null;
		}

		return Double.valueOf(NumberUtil.format(Double.valueOf(value.toString()) / 60));
	}

}
