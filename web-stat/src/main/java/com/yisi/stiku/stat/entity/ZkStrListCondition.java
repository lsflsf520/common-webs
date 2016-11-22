package com.yisi.stiku.stat.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import com.yisi.stiku.conf.ConfigOnZk;

/**
 * @author shangfeng
 *
 */
public class ZkStrListCondition extends ListCondition {

	private String nodePath;
	private String key;

	@Override
	public Map<String, String> getKvMap() {

		String[] valArr = ConfigOnZk.getValueArr(nodePath, key);
		Map<String, String> valMap = new LinkedHashMap<String, String>();
		if (valArr != null) {
			for (String val : valArr) {
				valMap.put(val, val);
			}
		}
		return valMap;
	}

	public String getNodePath() {

		return nodePath;
	}

	public void setNodePath(String nodePath) {

		this.nodePath = nodePath;
	}

	public String getKey() {

		return key;
	}

	public void setKey(String key) {

		this.key = key;
	}

}
