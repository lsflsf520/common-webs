package com.yisi.stiku.stat.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.esotericsoftware.minlog.Log;
import com.yisi.stiku.common.utils.BeanUtils;
import com.yisi.stiku.common.utils.BeanUtils.ObjConvertor;
import com.yisi.stiku.stat.consant.ChartType;
import com.yisi.stiku.stat.consant.StatConstant;
import com.yisi.stiku.stat.entity.Chart;
import com.yisi.stiku.stat.entity.Sery;

/**
 * @author shangfeng
 *
 */
public class LineDataLoader extends DefaultDataLoader {

	protected String axisXColumn;
	protected String axisYColumn;

	@Override
	protected List<Sery> buildSeries(List<String> legendDatas, Map<String, List<Object>> colValMap,
			Map<String, Object> paramMap) {

		List<Sery> seryList = new ArrayList<Sery>();
		for (Entry<String, List<Object>> entry : colValMap.entrySet()) {
			if (entry.getKey().equals(getAxisXColumn()) || entry.getKey().equals(getAxisYColumn())
					|| !legendDatas.contains(entry.getKey())) {
				continue;
			}
			String chartType = getChartType();
			String paramChartType = (String) paramMap.get(StatConstant.CHART_TYPE_KEY);
			if (StringUtils.isNotBlank(paramChartType)) {
				try {
					ChartType type = ChartType.valueOf(paramChartType);
					chartType = type.name();
				} catch (Exception e) {
					Log.warn("not support chart type '" + paramChartType + "'");
				}
			}
			Sery sery = new Sery(chartType);
			sery.setName(entry.getKey());
			sery.addAllData(entry.getValue());

			seryList.add(sery);
		}

		return seryList;
	}

	@Override
	protected void buildOtherChartData(Chart chart, List<String> legendDatas, Map<String, List<Object>> colValMap,
			Map<String, Object> paramMap) {

		super.buildOtherChartData(chart, legendDatas, colValMap, paramMap);
		List<Object> axisXList = colValMap.get(getAxisXColumn());
		List<String> axisXStrList = null;
		if (axisXList != null && !axisXList.isEmpty()) {
			axisXStrList = BeanUtils.convert2ObjList(axisXList, new ObjConvertor<String>() {

				@Override
				public String convert2Obj(Object obj) {

					return obj == null ? null : obj.toString();
				}
			});
		}

		List<Object> axisYList = colValMap.get(getAxisYColumn());
		List<String> axisYStrList = null;
		if (axisYList != null && !axisYList.isEmpty()) {
			axisYStrList = BeanUtils.convert2ObjList(axisYList, new ObjConvertor<String>() {

				@Override
				public String convert2Obj(Object obj) {

					return obj == null ? null : obj.toString();
				}
			});
		}

		chart.setAxisXData(axisXStrList);
		chart.setAxisYData(axisYStrList);
	}

	protected String getChartType() {

		return ChartType.line.name();
	}

	public String getAxisXColumn() {

		return axisXColumn;
	}

	public void setAxisXColumn(String axisXColumn) {

		this.axisXColumn = axisXColumn;
	}

	public String getAxisYColumn() {

		return axisYColumn;
	}

	public void setAxisYColumn(String axisYColumn) {

		this.axisYColumn = axisYColumn;
	}

}
