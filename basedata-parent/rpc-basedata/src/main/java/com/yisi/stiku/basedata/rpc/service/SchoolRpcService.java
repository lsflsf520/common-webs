package com.yisi.stiku.basedata.rpc.service;

import java.util.List;

import com.yisi.stiku.basedata.entity.TbSchool;

public interface SchoolRpcService extends BaseDataLoader{

	List<TbSchool> loadBySchoolIds(List<Long> schoolIdList);

	TbSchool findTbSchoolById(Long schoolId);
	
	List<TbSchool> findByProvinceId(Long provinceId);
	
	List<TbSchool> findByCityId(Long cityId);
	
	List<TbSchool> findByCountyId(Long cityId);
	
}
