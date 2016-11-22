package com.yisi.stiku.stat.loader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.stat.consant.ChartType;
import com.yisi.stiku.stat.entity.Sery;

/**
 * @author shangfeng
 *
 */
public class PieDataLoader extends DefaultDataLoader {

	private String seryName;

	@Override
	protected void checkData(List<Map<String, Object>> dataMapList) throws BaseRuntimeException {

		if (dataMapList == null || dataMapList.isEmpty() || dataMapList.size() > 1) {
			throw new BaseRuntimeException("ILLEGAL_DATA", "不正确的饼图数据格式");
		}
		if (dataMapList.get(0) == null) {
			dataMapList.remove(0);
		}
	}

	@Override
	protected List<Sery> buildSeries(List<String> legendDatas, Map<String, List<Object>> colValMap,
			Map<String, Object> paramMap) {

		List<Object> datas = new ArrayList<Object>();

		Map<String, String> dataDictMap = getDataDictMap();
		for (String legend : legendDatas) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("name",
					dataDictMap != null && StringUtils.isNotBlank(dataDictMap.get(legend)) ? dataDictMap.get(legend)
							: legend);

			List<Object> valList = colValMap.get(legend);
			data.put("value", valList == null || valList.isEmpty() ? 0 : valList.get(0));

			datas.add(data);
		}

		Sery sery = new Sery(ChartType.pie.name());
		sery.setName(seryName);
		sery.addAllData(datas);

		return Arrays.asList(sery);
	}

	public String getSeryName() {

		return seryName;
	}

	public void setSeryName(String seryName) {

		this.seryName = seryName;
	}

}
