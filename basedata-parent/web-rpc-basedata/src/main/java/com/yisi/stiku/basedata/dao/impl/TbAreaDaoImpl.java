package com.yisi.stiku.basedata.dao.impl;

import java.util.List;

import com.yisi.stiku.basedata.dao.TbAreaDao;
import com.yisi.stiku.basedata.entity.TbArea;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

@Repository
public class TbAreaDaoImpl extends BaseDaoImpl<Long, TbArea> {
    @Resource
    private TbAreaDao tbAreaDao;

    @Override
    protected BaseDao<Long, TbArea> getProxyBaseDao() {
        return tbAreaDao;
    }
    
    public List<TbArea> getSchoolProvinces(){
    	return this.getSqlSessionTemplate().selectList(getMapperNamespace() + ".getSchoolProvinces");
    }
}