package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TbProblemDao;
import com.yisi.stiku.basedata.entity.TbProblem;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TbProblemDaoImpl extends BaseDaoImpl<Long, TbProblem> {
    @Resource
    private TbProblemDao tbProblemDao;

    @Override
    protected BaseDao<Long, TbProblem> getProxyBaseDao() {
        return tbProblemDao;
    }
}