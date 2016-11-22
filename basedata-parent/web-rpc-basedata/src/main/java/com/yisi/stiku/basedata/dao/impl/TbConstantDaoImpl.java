package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TbConstantDao;
import com.yisi.stiku.basedata.entity.TbConstant;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TbConstantDaoImpl extends BaseDaoImpl<Long, TbConstant> {
    @Resource
    private TbConstantDao tbConstantDao;

    @Override
    protected BaseDao<Long, TbConstant> getProxyBaseDao() {
        return tbConstantDao;
    }
}