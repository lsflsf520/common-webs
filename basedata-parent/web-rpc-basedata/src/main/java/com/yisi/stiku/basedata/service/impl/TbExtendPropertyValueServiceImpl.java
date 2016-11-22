package com.yisi.stiku.basedata.service.impl;

import com.yisi.stiku.basedata.dao.impl.TbExtendPropertyValueDaoImpl;
import com.yisi.stiku.basedata.entity.TbExtendPropertyValue;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TbExtendPropertyValueServiceImpl extends BaseServiceImpl<Long, TbExtendPropertyValue> {
    @Resource
    private TbExtendPropertyValueDaoImpl tbExtendPropertyValueDaoImpl;

    @Override
    protected BaseDaoImpl<Long, TbExtendPropertyValue> getBaseDaoImpl() {
        return tbExtendPropertyValueDaoImpl;
    }
}