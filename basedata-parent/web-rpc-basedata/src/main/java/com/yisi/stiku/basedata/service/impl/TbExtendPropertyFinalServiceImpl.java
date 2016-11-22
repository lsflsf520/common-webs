package com.yisi.stiku.basedata.service.impl;

import com.yisi.stiku.basedata.dao.impl.TbExtendPropertyFinalDaoImpl;
import com.yisi.stiku.basedata.entity.TbExtendPropertyFinal;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TbExtendPropertyFinalServiceImpl extends BaseServiceImpl<Long, TbExtendPropertyFinal> {
    @Resource
    private TbExtendPropertyFinalDaoImpl tbExtendPropertyFinalDaoImpl;

    @Override
    protected BaseDaoImpl<Long, TbExtendPropertyFinal> getBaseDaoImpl() {
        return tbExtendPropertyFinalDaoImpl;
    }
}