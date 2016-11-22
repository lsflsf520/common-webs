package com.yisi.stiku.stat.entity;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.yisi.stiku.stat.consant.CondType;

/**
 * @author shangfeng
 *
 */
public class ListCondition extends Condition {

	protected String condType;
	protected Map<String, String> kvMap = new TreeMap<String, String>();

	public Map<String, String> getKvMap() {

		return Collections.unmodifiableMap(kvMap);
	}

	public void setKvMap(Map<String, String> kvMap) {

		if (kvMap != null && !kvMap.isEmpty()) {
			this.kvMap.putAll(kvMap);

		}
	}

	@Override
	public CondType getCondType() {

		return StringUtils.isBlank(condType) ? CondType.SELECT : CondType.valueOf(condType);
	}

	public void setCondType(String condType) {

		this.condType = condType;
	}

}
