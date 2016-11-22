package com.yisi.stiku.statdata.dao.impl;

import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.statdata.dao.GradeFenbanDataDao;
import com.yisi.stiku.statdata.entity.GradeFenbanData;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class GradeFenbanDataDaoImpl extends BaseDaoImpl<Integer, GradeFenbanData> {
    @Resource
    private GradeFenbanDataDao gradeFenbanDataDao;

    @Override
    protected BaseDao<Integer, GradeFenbanData> getProxyBaseDao() {
        return gradeFenbanDataDao;
    }
}