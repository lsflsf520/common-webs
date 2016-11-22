package com.yisi.stiku.basedata.service.impl;

import com.yisi.stiku.basedata.dao.impl.TbSubjectDaoImpl;
import com.yisi.stiku.basedata.entity.TbSubject;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TbSubjectServiceImpl extends BaseServiceImpl<Long, TbSubject> {
    @Resource
    private TbSubjectDaoImpl tbSubjectDaoImpl;

    @Override
    protected BaseDaoImpl<Long, TbSubject> getBaseDaoImpl() {
        return tbSubjectDaoImpl;
    }
}