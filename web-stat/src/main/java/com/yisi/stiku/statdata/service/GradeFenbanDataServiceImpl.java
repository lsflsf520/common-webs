package com.yisi.stiku.statdata.service;

import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.statdata.dao.impl.GradeFenbanDataDaoImpl;
import com.yisi.stiku.statdata.entity.GradeFenbanData;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class GradeFenbanDataServiceImpl extends BaseServiceImpl<Integer, GradeFenbanData> {
    @Resource
    private GradeFenbanDataDaoImpl gradeFenbanDataDaoImpl;

    @Override
    protected BaseDaoImpl<Integer, GradeFenbanData> getBaseDaoImpl() {
        return gradeFenbanDataDaoImpl;
    }
}