package com.yisi.stiku.stat.entity;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.stat.loader.dao.QueryDao;

/**
 * @author shangfeng
 *
 */
public class SqlListCondition extends ListCondition {

	private String dsKey; // 数据源sqlSessionTemplate的key
	private String sql;
	private String keyName;
	private String valName;

	@Resource
	private QueryDao queryDao;

	private long lastUpTime = 0;

	public String getSql() {

		return sql;
	}

	public void setSql(String sql) {

		this.sql = sql;
	}

	public String getDsKey() {

		return dsKey;
	}

	public void setDsKey(String dsKey) {

		this.dsKey = dsKey;
	}

	public String getKeyName() {

		return keyName;
	}

	public void setKeyName(String keyName) {

		this.keyName = keyName;
	}

	public String getValName() {

		return valName;
	}

	public void setValName(String valName) {

		this.valName = valName;
	}

	@Override
	public Map<String, String> getKvMap() {

		if (kvMap.isEmpty() || System.currentTimeMillis() - lastUpTime > 8l * 3600 * 1000 /*
																						 * 8
																						 * 小時需要更新一次
																						 */) {
			List<Map<String, Object>> valMapList = queryDao.queryData(StringUtils.isBlank(getDsKey()) ? "" : getDsKey(),
					getSql(), null);

			if (valMapList != null && !valMapList.isEmpty()) {
				for (Map<String, Object> valMap : valMapList) {
					Object k = valMap.get(getKeyName());
					Object v = valMap.get(getValName());
					if (k == null) {
						throw new BaseRuntimeException("ILLEGAL_DATA", "key cannot be null for list condition. valMap:"
								+ valMap);
					}
					kvMap.put(k.toString(), v == null ? null : v.toString());
				}

				lastUpTime = System.currentTimeMillis();
			}
		}

		return super.getKvMap();
	}

	public void setQueryDao(QueryDao queryDao) {

		this.queryDao = queryDao;

	}

}
