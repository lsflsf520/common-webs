package com.yisi.stiku.basedata.rpc.service;

/**
 * 
 * @author shangfeng
 *
 */
public interface BaseDataLoader {

	/**
	 * 
	 * @param schoolId 
	 * @return 根据schoolId返回countyId
	 */
	String loadCountyIdBySchoolId(Long schoolId);
	
}
