package com.yisi.stiku.stat.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.stat.entity.Chart;
import com.yisi.stiku.stat.entity.PageData;
import com.yisi.stiku.stat.entity.Sery;
import com.yisi.stiku.stat.entity.TableData;
import com.yisi.stiku.stat.fmt.Formatter;
import com.yisi.stiku.stat.loader.dao.QueryDao;

/**
 * @author shangfeng
 *
 */
public abstract class DefaultDataLoader implements DataLoader {

	// private final static Logger LOG =
	// LoggerFactory.getLogger(DefaultDataLoader.class);

	protected String divId;
	protected String url;
	protected String title;
	protected String subTitle;
	protected String titleX;
	protected String titleY;
	protected List<String> legendData;

	protected List<String> condKeys;

	protected String dsKey;
	protected String statement;

	protected List<String> colTitleList;

	protected int pageNum = 20;

	protected String tableTitle;

	protected Map<String, String> dataDictMap;

	protected Map<String, Formatter> fmtMap;

	@Resource
	private QueryDao queryDao;

	protected List<Map<String, Object>> queryData(Map<String, Object>
			paramMap) {

		List<Map<String, Object>> dataList = queryDao
				.queryData(StringUtils.isBlank(dsKey) ? "" : dsKey, statement, paramMap);
		if (fmtMap != null && !fmtMap.isEmpty() && dataList != null && !dataList.isEmpty()) {
			// 此处对数值进行指定的格式化操作
			for (Map<String, Object> dataMap : dataList) {
				if (dataMap != null) {
					for (String key : dataMap.keySet()) {
						Formatter fmt = fmtMap.get(key);
						if (fmt != null) {
							Object value = dataMap.get(key);
							dataMap.put(key, fmt.format2Str(value));
						}
					}
				}
			}
		}

		return dataList;
	}

	@Override
	public PageData buildPageData(Map<String, Object> paramMap) {

		List<Map<String, Object>> datas = queryData(paramMap);

		checkData(datas); // 子类可以重写该方法，对数据进行预先检查

		PageData pageData = new PageData();
		pageData.setUrl(getUrl());
		pageData.setPrimDbDataList(datas);
		pageData.addDataDictMap(getDataDictMap());

		TableData tableData = new TableData();
		tableData.setColTitleList(getColTitleList());
		tableData.setTableTitle(getTableTitle());
		tableData.setPageNum(getPageNum());

		pageData.setTableData(tableData);

		Chart chart = buildChart(datas, paramMap);

		pageData.setChart(chart);

		return pageData;
	}

	protected Chart buildChart(List<Map<String, Object>> dbDataList, Map<String, Object> paramMap) {

		List<String> legendDatas = getLegendData();
		if (legendDatas == null || legendDatas.isEmpty()) {
			throw new BaseRuntimeException("NO_LEGEND", "属性legendData不能为空");
		}

		Map<String, List<Object>> colValMap = new HashMap<String, List<Object>>();
		// 下边的嵌套循环就是为了把从db查询到的数据组装成 列名 -> List<值> 的kv结构
		for (Map<String, Object> dataMap : dbDataList) {
			if (dataMap == null) {
				continue;
			}
			for (Entry<String, Object> entry : dataMap.entrySet()) {
				List<Object> valList = colValMap.get(entry.getKey());
				if (valList == null) {
					valList = new ArrayList<Object>();
					colValMap.put(entry.getKey(), valList);
				}

				valList.add(entry.getValue());
			}
		}

		List<Sery> series = buildSeries(legendDatas, colValMap, paramMap);

		Chart chart = new Chart();
		chart.setDivId(getDivId());
		chart.setTitle(getTitle());
		chart.setSubTitle(getSubTitle());
		chart.setLegendData(legendDatas);
		chart.setSeries(series);

		buildOtherChartData(chart, legendDatas, colValMap, paramMap);

		return chart;
	}

	protected void checkData(List<Map<String, Object>> dataMapList) throws BaseRuntimeException {

		// do nothing
	}

	protected void buildOtherChartData(Chart chart, List<String> legendDatas, Map<String, List<Object>> colValMap,
			Map<String, Object> paramMap) {

		// do nothing

	}

	abstract protected List<Sery> buildSeries(List<String> legendDatas, Map<String, List<Object>> colValMap,
			Map<String, Object> paramMap);

	@Override
	public String getDivId() {

		return divId;
	}

	public void setDivId(String divId) {

		this.divId = divId;
	}

	@Override
	public String getUrl() {

		return url;
	}

	public void setUrl(String url) {

		this.url = url;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public void setSubTitle(String subTitle) {

		this.subTitle = subTitle;
	}

	@Override
	public String getTitle() {

		return this.title;
	}

	@Override
	public String getSubTitle() {

		return this.subTitle;
	}

	@Override
	public List<String> getLegendData() {

		return legendData;
	}

	public void setLegendData(List<String> legendData) {

		this.legendData = legendData;
	}

	@Override
	public List<String> getColTitleList() {

		return colTitleList;
	}

	public void setColTitleList(List<String> colTitleList) {

		this.colTitleList = colTitleList;
	}

	public String getDsKey() {

		return dsKey;
	}

	public void setDsKey(String dsKey) {

		this.dsKey = dsKey;
	}

	public Map<String, String> getDataDictMap() {

		return dataDictMap;
	}

	public void setDataDictMap(Map<String, String> dataDictMap) {

		this.dataDictMap = dataDictMap;
	}

	public String getStatement() {

		return statement;
	}

	public void setStatement(String statement) {

		this.statement = statement;
	}

	public String getTitleX() {

		return titleX;
	}

	public void setTitleX(String titleX) {

		this.titleX = titleX;
	}

	public String getTitleY() {

		return titleY;
	}

	public void setTitleY(String titleY) {

		this.titleY = titleY;
	}

	public List<String> getCondKeys() {

		return condKeys;
	}

	public void setCondKeys(List<String> condKeys) {

		this.condKeys = condKeys;
	}

	public Map<String, Formatter> getFmtMap() {

		return fmtMap;
	}

	public void setFmtMap(Map<String, Formatter> fmtMap) {

		this.fmtMap = fmtMap;
	}

	@Override
	public int getPageNum() {

		return pageNum;
	}

	public void setPageNum(int pageNum) {

		this.pageNum = pageNum;
	}

	@Override
	public String getTableTitle() {

		return tableTitle;
	}

	public void setTableTitle(String tableTitle) {

		this.tableTitle = tableTitle;
	}

}
