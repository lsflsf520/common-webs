// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst 
// Source File Name:   StringUtil.java

package com.yisi.stiku.quartz.util;

public class StringUtil {

	public StringUtil() {
	}

	public static boolean isSameTextValue(String string1, String string2) {
		if (string1 == null || string2 == null)
			return string1 == null && string2 == null;
		return string1.trim().equalsIgnoreCase(string2.trim());
	}

	public static boolean isEmpty(String s) {
		boolean flg = true;
		if (s != null && s.trim().length() > 0)
			return false;
		else
			return flg;
	}
}
