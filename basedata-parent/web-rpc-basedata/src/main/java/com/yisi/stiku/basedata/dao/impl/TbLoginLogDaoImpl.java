package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TbLoginLogDao;
import com.yisi.stiku.basedata.entity.TbLoginLog;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TbLoginLogDaoImpl extends BaseDaoImpl<String, TbLoginLog> {
    @Resource
    private TbLoginLogDao tbLoginLogDao;

    @Override
    protected BaseDao<String, TbLoginLog> getProxyBaseDao() {
        return tbLoginLogDao;
    }
}