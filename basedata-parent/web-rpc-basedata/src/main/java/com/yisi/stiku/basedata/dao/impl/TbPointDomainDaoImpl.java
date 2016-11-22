package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TbPointDomainDao;
import com.yisi.stiku.basedata.entity.TbPointDomain;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TbPointDomainDaoImpl extends BaseDaoImpl<Long, TbPointDomain> {
    @Resource
    private TbPointDomainDao tbPointDomainDao;

    @Override
    protected BaseDao<Long, TbPointDomain> getProxyBaseDao() {
        return tbPointDomainDao;
    }
}