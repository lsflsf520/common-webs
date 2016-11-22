package com.yisi.stiku.basedata.service.impl;

import com.yisi.stiku.basedata.dao.impl.TbProblemModelPromDaoImpl;
import com.yisi.stiku.basedata.entity.TbProblemModelProm;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TbProblemModelPromServiceImpl extends BaseServiceImpl<Long, TbProblemModelProm> {
    @Resource
    private TbProblemModelPromDaoImpl tbProblemModelPromDaoImpl;

    @Override
    protected BaseDaoImpl<Long, TbProblemModelProm> getBaseDaoImpl() {
        return tbProblemModelPromDaoImpl;
    }
}