/*package com.yisi.stiku.quartz.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log extends MyLogger {

	Log(org.apache.log4j.Logger logger) {
		super(logger);
	}

	private static final long serialVersionUID = 9146460294984453934L;

 *//**
 * 获得Logger
 * 
 * @param name
 *            自定义的日志发出者名称
 * @return Logger
 */
/*
public Logger get(String name) {
return LoggerFactory.getLogger(name);
}

 *//**
 * Error等级日志<br>
 * 由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用！！
 * 
 * @param log
 *            日志对象
 * @param e
 *            需在日志中堆栈打印的异常
 * @param format
 *            格式文本，{} 代表变量
 * @param arguments
 *            变量对应的参数
 */
/*
public void error(Throwable e, String format, Object... arguments) {
super.error(format(format, arguments), e);
}

 *//**
 * 格式化文本
 * 
 * @param template
 *            文本模板，被替换的部分用 {} 表示
 * @param values
 *            参数值
 * @return 格式化后的文本
 */
/*
private String format(String template, Object... values) {
return String.format(template.replace("{}", "%s"), values);
}

 *//**
 * Trace等级日志，小于Debug
 * 
 * @param log
 *            日志对象
 * @param format
 *            格式文本，{} 代表变量
 * @param arguments
 *            变量对应的参数
 */
/*
public void trace(String format, Object... arguments) {
super.trace(format, arguments);
}

// ------------------------ debug

 *//**
 * Debug等级日志，小于Info
 * 
 * @param log
 *            日志对象
 * @param format
 *            格式文本，{} 代表变量
 * @param arguments
 *            变量对应的参数
 */
/*
public void debug(String format, Object... arguments) {
super.debug(format, arguments);
}

 *//**
 * Info等级日志，小于Warn
 * 
 * @param log
 *            日志对象
 * @param format
 *            格式文本，{} 代表变量
 * @param arguments
 *            变量对应的参数
 */
/*
public void info(String format, Object... arguments) {
super.info(format, arguments);
}

 *//**
 * Warn等级日志，小于Error
 * 
 * @param log
 *            日志对象
 * @param format
 *            格式文本，{} 代表变量
 * @param arguments
 *            变量对应的参数
 */
/*
public void warn(String format, Object... arguments) {
super.warn(format, arguments);
}

 *//**
 * Warn等级日志，小于Error
 * 
 * @param log
 *            日志对象
 * @param e
 *            需在日志中堆栈打印的异常
 * @param format
 *            格式文本，{} 代表变量
 * @param arguments
 *            变量对应的参数
 */
/*
public void warn(Throwable e, String format, Object... arguments) {
super.warn(format(format, arguments), e);
}

// ------------------------ error

 *//**
 * Error等级日志<br>
 * 
 * @param log
 *            日志对象
 * @param format
 *            格式文本，{} 代表变量
 * @param arguments
 *            变量对应的参数
 */
/*
 * public void error(String format, Object... arguments) { super.error(format,
 * arguments); }
 * 
 * }
 */