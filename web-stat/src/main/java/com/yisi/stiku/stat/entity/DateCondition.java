package com.yisi.stiku.stat.entity;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

import com.yisi.stiku.common.utils.DateUtil;
import com.yisi.stiku.stat.consant.CondType;

/**
 * @author shangfeng
 *
 */
public class DateCondition extends Condition {

	private int time;
	private TimeUnit timeUnit;
	private String pattern;

	@Override
	public String getDefaultVal() {

		Date date = DateUtil.timeAdd(new Date(), time, timeUnit == null ? TimeUnit.DAYS : timeUnit);
		return DateUtil.formatDate(date, StringUtils.isBlank(pattern) ? DateUtil.FORMAT_DATE : pattern);

	}

	public int getTime() {

		return time;
	}

	public void setTime(int time) {

		this.time = time;
	}

	public TimeUnit getTimeUnit() {

		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {

		this.timeUnit = timeUnit;
	}

	public String getPattern() {

		return pattern;
	}

	public void setPattern(String pattern) {

		this.pattern = pattern;
	}

	@Override
	public CondType getCondType() {

		return CondType.DATE;
	}

}
