package com.yisi.stiku.stat.entity;

import com.yisi.stiku.stat.consant.CondType;

/**
 * 构建查询条件
 * 
 * @author shangfeng
 *
 */
public class Condition {

	protected String label; // 表单域的显示名称
	protected String name; // 表单域的名称
	protected String defaultVal; // 表单域的初始化默认值

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getDefaultVal() {

		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {

		this.defaultVal = defaultVal;
	}

	public String getLabel() {

		return label;
	}

	public void setLabel(String label) {

		this.label = label;
	}

	/**
	 * html表单域的类型
	 * 
	 * @return the condType
	 */
	public CondType getCondType() {

		return CondType.NORMAL;
	}

	/**
	 * 
	 * @return 默认情况下，只有checkbox类型的条件才可以传多个值
	 */
	public boolean isMultiValue() {

		return CondType.CHECKBOX.equals(getCondType());
	}

	public boolean isGroup() {

		return false;
	}

}
