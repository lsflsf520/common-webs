package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TbExtendPropertyDao;
import com.yisi.stiku.basedata.entity.TbExtendProperty;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TbExtendPropertyDaoImpl extends BaseDaoImpl<Long, TbExtendProperty> {
    @Resource
    private TbExtendPropertyDao tbExtendPropertyDao;

    @Override
    protected BaseDao<Long, TbExtendProperty> getProxyBaseDao() {
        return tbExtendPropertyDao;
    }
}