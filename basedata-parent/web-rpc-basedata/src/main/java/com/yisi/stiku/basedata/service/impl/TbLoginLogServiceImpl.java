package com.yisi.stiku.basedata.service.impl;

import com.yisi.stiku.basedata.dao.impl.TbLoginLogDaoImpl;
import com.yisi.stiku.basedata.entity.TbLoginLog;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TbLoginLogServiceImpl extends BaseServiceImpl<String, TbLoginLog> {
    @Resource
    private TbLoginLogDaoImpl tbLoginLogDaoImpl;

    @Override
    protected BaseDaoImpl<String, TbLoginLog> getBaseDaoImpl() {
        return tbLoginLogDaoImpl;
    }
}