package com.yisi.stiku.stat.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.stat.entity.Chart;
import com.yisi.stiku.stat.entity.Condition;
import com.yisi.stiku.stat.entity.PageData;
import com.yisi.stiku.stat.entity.Sery;
import com.yisi.stiku.stat.loader.DataLoader;

/**
 * @author shangfeng
 *
 */
@Component
public class PageDataBuilder implements ApplicationContextAware {

	private Map<String /* beanId */, DataLoader> loaderMap;

	private Map<String /* beanId */, Condition> condMap;

	private Map<String /* condition name */, Condition> name2CondMap = new HashMap<String, Condition>();

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {

		loaderMap = context.getBeansOfType(DataLoader.class);

		condMap = context.getBeansOfType(Condition.class);
		if (condMap != null && !condMap.isEmpty()) {
			for (Condition cond : condMap.values()) {
				name2CondMap.put(cond.getName(), cond);
			}
		}
	}

	private DataLoader getDataLoader(String beanName) {

		DataLoader dataLoader = null;
		if (loaderMap == null || (dataLoader = loaderMap.get(beanName)) == null) {
			throw new BaseRuntimeException("NOT_SUPPORT_MODULE", "对不起，系统没有对应的数据加载器！", "no data loader for '" + beanName
					+ "'");
		}

		return dataLoader;
	}

	public boolean isMultiCondition(String key) {

		Condition cond = name2CondMap.get(key);
		return cond != null && cond.isMultiValue();
	}

	public List<Condition> buildCondition(String beanName) {

		if (condMap == null || condMap.isEmpty()) {
			throw new BaseRuntimeException("NO_COND_BEAN_DEFINED",
					"There is not any bean of class 'com.yisi.stiku.stat.entity.Condition' defined in Spring");
		}

		DataLoader dataLoader = getDataLoader(beanName);

		List<Condition> condList = new ArrayList<Condition>();
		List<String> condKeys = dataLoader.getCondKeys();
		if (condKeys != null && !condKeys.isEmpty()) {
			for (String condKey : condKeys) {
				Condition cond = condMap.get(condKey);
				if (cond == null) {
					throw new BaseRuntimeException("NOT_EXIST",
							"not found bean of class 'com.yisi.stiku.stat.entity.Condition' with key '" + condKey + "'");
				}
				condList.add(cond);
			}
		}

		return condList;
	}

	public PageData buildPageData(String beanName, Map<String, Object> paramMap) {

		DataLoader dataLoader = getDataLoader(beanName);

		PageData pageData = dataLoader.buildPageData(paramMap);

		Chart chart = pageData.getChart();
		if (chart != null) {
			chart.setTitleX(dataLoader.getTitleX());
			chart.setTitleY(dataLoader.getTitleY());

			// 如果有数据字典，则需将对应的数据做相应的转换。即将指定的字段名字修改为数据字典中所能匹配到的字段名
			Map<String, String> dataDictMap = dataLoader.getDataDictMap();
			if (dataDictMap != null && !dataDictMap.isEmpty()) {
				if (StringUtils.isNotBlank(dataDictMap.get(chart.getTitleX()))) {
					chart.setTitleX(dataDictMap.get(chart.getTitleX()));
				}
				if (StringUtils.isNotBlank(dataDictMap.get(chart.getTitleY()))) {
					chart.setTitleY(dataDictMap.get(chart.getTitleY()));
				}
				List<String> legendCnList = convertByDataDict(dataDictMap, chart.getLegendData());
				chart.setLegendData(legendCnList);

				List<String> axisXCnList = convertByDataDict(dataDictMap, chart.getAxisXData());
				chart.setAxisXData(axisXCnList);

				List<String> axisYCnList = convertByDataDict(dataDictMap, chart.getAxisYData());
				chart.setAxisYData(axisYCnList);

				List<Sery> series = chart.getSeries();
				if (series != null && !series.isEmpty()) {
					for (Sery sery : series) {
						String value = dataDictMap.get(sery.getName());
						if (StringUtils.isNotBlank(value)) {
							sery.setName(value);
						}
					}
				}
			}
		}

		return pageData;
	}

	private List<String> convertByDataDict(Map<String, String> dataDictMap, List<String> legendList) {

		if (legendList == null || legendList.isEmpty()) {
			return legendList;
		}
		List<String> legendCnList = new ArrayList<String>();
		for (String key : legendList) {
			String value = dataDictMap.get(key);

			legendCnList.add(StringUtils.isNotBlank(value) ? value : key);
		}

		return legendCnList;
	}
}
