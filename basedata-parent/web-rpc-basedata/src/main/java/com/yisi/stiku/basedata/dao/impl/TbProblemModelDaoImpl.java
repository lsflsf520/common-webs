package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TbProblemModelDao;
import com.yisi.stiku.basedata.entity.TbProblemModel;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TbProblemModelDaoImpl extends BaseDaoImpl<Long, TbProblemModel> {
    @Resource
    private TbProblemModelDao tbProblemModelDao;

    @Override
    protected BaseDao<Long, TbProblemModel> getProxyBaseDao() {
        return tbProblemModelDao;
    }
}