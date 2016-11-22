package com.yisi.stiku.statdata.entity;

public class PerCentFractionUtil {

	public static String parseViewPercent(Integer pamamter)
	{

		return (pamamter / 10.0) + "%";
	}

	public static Double parseViewFraction(Integer pamamter)
	{

		return pamamter / 10.0;
	}
}
