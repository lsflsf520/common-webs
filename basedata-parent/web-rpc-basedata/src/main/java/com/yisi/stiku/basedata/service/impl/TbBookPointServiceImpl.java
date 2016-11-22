package com.yisi.stiku.basedata.service.impl;

import com.yisi.stiku.basedata.dao.impl.TbBookPointDaoImpl;
import com.yisi.stiku.basedata.entity.TbBookPoint;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TbBookPointServiceImpl extends BaseServiceImpl<Long, TbBookPoint> {
    @Resource
    private TbBookPointDaoImpl tbBookPointDaoImpl;

    @Override
    protected BaseDaoImpl<Long, TbBookPoint> getBaseDaoImpl() {
        return tbBookPointDaoImpl;
    }
}