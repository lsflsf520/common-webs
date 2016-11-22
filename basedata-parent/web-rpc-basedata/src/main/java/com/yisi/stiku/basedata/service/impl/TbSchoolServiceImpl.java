package com.yisi.stiku.basedata.service.impl;

import java.util.List;

import com.yisi.stiku.basedata.dao.impl.TbSchoolDaoImpl;
import com.yisi.stiku.basedata.entity.TbSchool;
import com.yisi.stiku.basedata.rpc.service.SchoolRpcService;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.rpc.annotation.RpcService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
@RpcService(SchoolRpcService.class)
public class TbSchoolServiceImpl extends BaseServiceImpl<Long, TbSchool> implements SchoolRpcService{
    @Resource
    private TbSchoolDaoImpl tbSchoolDaoImpl;

    @Override
    protected BaseDaoImpl<Long, TbSchool> getBaseDaoImpl() {
        return tbSchoolDaoImpl;
    }

	@Override
	public String loadCountyIdBySchoolId(Long schoolId) {
		TbSchool school = this.findById(schoolId);
		return school == null ? null : school.getCountyId() + "";
	}

	@Override
	public List<TbSchool> loadBySchoolIds(List<Long> schoolIdList) {
		return tbSchoolDaoImpl.loadBySchoolIds(schoolIdList);
	}

	@Override
	public TbSchool findTbSchoolById(Long schoolId) {
		TbSchool tbSchool = new TbSchool();
		tbSchool.setId(schoolId);
		List<TbSchool> tbSchoolList = tbSchoolDaoImpl.findByEntity(tbSchool);
		if(tbSchoolList!=null && tbSchoolList.size()>0) {
			return tbSchoolList.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public List<TbSchool> findByProvinceId(Long provinceId) {
		TbSchool query = new TbSchool();
		query.setProvinceId(provinceId);
		
		return this.findByEntity(query);
	}
	
	@Override
	public List<TbSchool> findByCityId(Long cityId) {
		TbSchool query = new TbSchool();
		query.setCityId(cityId);
		
		return this.findByEntity(query);
	}
	
	@Override
	public List<TbSchool> findByCountyId(Long countyId) {
		TbSchool query = new TbSchool();
		query.setCountyId(countyId);
		
		return this.findByEntity(query);
	}

}