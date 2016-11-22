package com.yisi.stiku.quartz.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {

	public static final String DATE_FORMAT_PATTERN = "yy.MM.dd hh:mm";
	static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"yy.MM.dd hh:mm");

	public FormatUtil() {
	}

	public static String getDateAsString(Date date) {
		if (date == null)
			return null;
		else
			return dateFormatter.format(date);
	}

	public static Date parseStringToDate(String dateStr) throws ParseException {
		if (dateStr == null)
			return null;
		else
			return dateFormatter.parse(dateStr);
	}

}
