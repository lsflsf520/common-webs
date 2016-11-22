package com.yisi.stiku.basedata.service.impl;

import com.yisi.stiku.basedata.dao.impl.TbExtendPropertyDaoImpl;
import com.yisi.stiku.basedata.entity.TbExtendProperty;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TbExtendPropertyServiceImpl extends BaseServiceImpl<Long, TbExtendProperty> {
    @Resource
    private TbExtendPropertyDaoImpl tbExtendPropertyDaoImpl;

    @Override
    protected BaseDaoImpl<Long, TbExtendProperty> getBaseDaoImpl() {
        return tbExtendPropertyDaoImpl;
    }
}