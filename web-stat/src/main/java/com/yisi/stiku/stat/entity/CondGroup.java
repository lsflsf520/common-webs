package com.yisi.stiku.stat.entity;

import java.util.List;

/**
 * @author shangfeng
 *
 */
public class CondGroup extends Condition {

	private List<Condition> condList;

	/**
	 * 
	 */
	public CondGroup(List<Condition> condList) {

		this.condList = condList;
	}

	public List<Condition> getCondList() {

		return condList;
	}

	public boolean isGroup() {

		return true;
	}

}
