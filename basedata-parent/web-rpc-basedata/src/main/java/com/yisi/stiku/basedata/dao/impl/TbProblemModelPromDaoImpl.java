package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TbProblemModelPromDao;
import com.yisi.stiku.basedata.entity.TbProblemModelProm;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TbProblemModelPromDaoImpl extends BaseDaoImpl<Long, TbProblemModelProm> {
    @Resource
    private TbProblemModelPromDao tbProblemModelPromDao;

    @Override
    protected BaseDao<Long, TbProblemModelProm> getProxyBaseDao() {
        return tbProblemModelPromDao;
    }
}