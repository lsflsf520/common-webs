package com.yisi.stiku.stat.loader;

import java.util.List;
import java.util.Map;

import com.yisi.stiku.stat.entity.PageData;

/**
 * @author shangfeng
 *
 */
public interface DataLoader {

	public String getDivId();

	public String getUrl();

	public String getTitle();

	public String getTitleX();

	public String getTitleY();

	public String getSubTitle();

	public List<String> getLegendData();

	public List<String> getCondKeys();

	public Map<String, String> getDataDictMap();

	public String getTableTitle();

	public List<String> getColTitleList();

	public int getPageNum();

	public PageData buildPageData(Map<String, Object> paramMap);

}
