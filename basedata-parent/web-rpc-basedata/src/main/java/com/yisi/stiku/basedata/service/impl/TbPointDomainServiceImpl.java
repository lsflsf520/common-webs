package com.yisi.stiku.basedata.service.impl;

import com.yisi.stiku.basedata.dao.impl.TbPointDomainDaoImpl;
import com.yisi.stiku.basedata.entity.TbPointDomain;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TbPointDomainServiceImpl extends BaseServiceImpl<Long, TbPointDomain> {
    @Resource
    private TbPointDomainDaoImpl tbPointDomainDaoImpl;

    @Override
    protected BaseDaoImpl<Long, TbPointDomain> getBaseDaoImpl() {
        return tbPointDomainDaoImpl;
    }
}