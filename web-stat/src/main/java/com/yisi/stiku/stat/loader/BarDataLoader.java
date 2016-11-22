package com.yisi.stiku.stat.loader;

import com.yisi.stiku.stat.consant.ChartType;

/**
 * @author shangfeng
 *
 */
public class BarDataLoader extends LineDataLoader {

	@Override
	protected String getChartType() {

		return ChartType.bar.name();
	}

}
