package com.yisi.stiku.basedata.service.impl;

import com.yisi.stiku.basedata.dao.impl.TbExamPointDaoImpl;
import com.yisi.stiku.basedata.entity.TbExamPoint;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TbExamPointServiceImpl extends BaseServiceImpl<Long, TbExamPoint> {
    @Resource
    private TbExamPointDaoImpl tbExamPointDaoImpl;

    @Override
    protected BaseDaoImpl<Long, TbExamPoint> getBaseDaoImpl() {
        return tbExamPointDaoImpl;
    }
}