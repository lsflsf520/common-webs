package com.yisi.stiku.intg.dao.impl;

import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.intg.dao.DevDemandDao;
import com.yisi.stiku.intg.entity.DevDemand;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class DevDemandDaoImpl extends BaseDaoImpl<Integer, DevDemand> {
    @Resource
    private DevDemandDao devDemandDao;

    @Override
    protected BaseDao<Integer, DevDemand> getProxyBaseDao() {
        return devDemandDao;
    }
}