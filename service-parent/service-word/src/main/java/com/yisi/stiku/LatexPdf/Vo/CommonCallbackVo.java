package com.yisi.stiku.LatexPdf.Vo;

public class CommonCallbackVo {
	private final String clazzName;
	private final String version;
	private final String methodName;
	
	/**
	 * @param clazzName 回调类名
	 * @param version 回调版本
	 * @param methodName 回调方法名
	 */
	public CommonCallbackVo(String clazzName, String version, String methodName) {
		super();
		this.clazzName = clazzName;
		this.version = version;
		this.methodName = methodName;
	}
	public String getClazzName() {
		return clazzName;
	}
	public String getVersion() {
		return version;
	}
	public String getMethodName() {
		return methodName;
	}
	
}
