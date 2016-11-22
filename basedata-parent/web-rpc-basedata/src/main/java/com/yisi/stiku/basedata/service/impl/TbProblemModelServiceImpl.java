package com.yisi.stiku.basedata.service.impl;

import com.yisi.stiku.basedata.dao.impl.TbProblemModelDaoImpl;
import com.yisi.stiku.basedata.entity.TbProblemModel;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TbProblemModelServiceImpl extends BaseServiceImpl<Long, TbProblemModel> {
    @Resource
    private TbProblemModelDaoImpl tbProblemModelDaoImpl;

    @Override
    protected BaseDaoImpl<Long, TbProblemModel> getBaseDaoImpl() {
        return tbProblemModelDaoImpl;
    }
}