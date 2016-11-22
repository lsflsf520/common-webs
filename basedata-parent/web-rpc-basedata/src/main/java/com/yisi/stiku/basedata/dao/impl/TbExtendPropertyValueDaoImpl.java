package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TbExtendPropertyValueDao;
import com.yisi.stiku.basedata.entity.TbExtendPropertyValue;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TbExtendPropertyValueDaoImpl extends BaseDaoImpl<Long, TbExtendPropertyValue> {
    @Resource
    private TbExtendPropertyValueDao tbExtendPropertyValueDao;

    @Override
    protected BaseDao<Long, TbExtendPropertyValue> getProxyBaseDao() {
        return tbExtendPropertyValueDao;
    }
}