package com.yisi.stiku.statdata.dao.impl;

import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.statdata.dao.GradeFenbanProblemDataDao;
import com.yisi.stiku.statdata.entity.GradeFenbanProblemData;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class GradeFenbanProblemDataDaoImpl extends BaseDaoImpl<Integer, GradeFenbanProblemData> {
    @Resource
    private GradeFenbanProblemDataDao gradeFenbanProblemDataDao;

    @Override
    protected BaseDao<Integer, GradeFenbanProblemData> getProxyBaseDao() {
        return gradeFenbanProblemDataDao;
    }
}