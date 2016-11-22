package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TbExtendPropertyFinalDao;
import com.yisi.stiku.basedata.entity.TbExtendPropertyFinal;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TbExtendPropertyFinalDaoImpl extends BaseDaoImpl<Long, TbExtendPropertyFinal> {
    @Resource
    private TbExtendPropertyFinalDao tbExtendPropertyFinalDao;

    @Override
    protected BaseDao<Long, TbExtendPropertyFinal> getProxyBaseDao() {
        return tbExtendPropertyFinalDao;
    }
}