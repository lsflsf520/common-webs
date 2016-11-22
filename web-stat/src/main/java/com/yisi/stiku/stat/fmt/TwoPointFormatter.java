package com.yisi.stiku.stat.fmt;

import java.math.BigDecimal;

import com.yisi.stiku.common.utils.NumberUtil;

/**
 * 将浮点数格式化为2位小数点
 * 
 * @author shangfeng
 *
 */
public class TwoPointFormatter implements Formatter {

	@Override
	public Object format2Str(Object value) {

		if (value instanceof Double || value instanceof Float || value instanceof BigDecimal) {
			Double val = Double.valueOf(value.toString());

			return Double.valueOf(NumberUtil.format(val));
		}

		return value == null ? null : Double.valueOf(value.toString());
	}
}
