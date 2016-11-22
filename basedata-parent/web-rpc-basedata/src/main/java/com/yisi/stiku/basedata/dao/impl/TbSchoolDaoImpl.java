package com.yisi.stiku.basedata.dao.impl;

import java.util.List;

import com.yisi.stiku.basedata.dao.TbSchoolDao;
import com.yisi.stiku.basedata.entity.TbSchool;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

@Repository
public class TbSchoolDaoImpl extends BaseDaoImpl<Long, TbSchool> {
    @Resource
    private TbSchoolDao tbSchoolDao;

    @Override
    protected BaseDao<Long, TbSchool> getProxyBaseDao() {
        return tbSchoolDao;
    }
    
    public List<TbSchool> loadBySchoolIds(List<Long> schoolIdList) {
		return this.getSqlSessionTemplate().selectList(getMapperNamespace() + ".loadBySchoolIds", schoolIdList);
	}
}