package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TbBookPointDao;
import com.yisi.stiku.basedata.entity.TbBookPoint;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TbBookPointDaoImpl extends BaseDaoImpl<Long, TbBookPoint> {
    @Resource
    private TbBookPointDao tbBookPointDao;

    @Override
    protected BaseDao<Long, TbBookPoint> getProxyBaseDao() {
        return tbBookPointDao;
    }
}