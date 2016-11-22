package com.yisi.stiku.basedata.service.impl;

import com.yisi.stiku.basedata.dao.impl.TbPracticeExamDaoImpl;
import com.yisi.stiku.basedata.entity.TbPracticeExam;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TbPracticeExamServiceImpl extends BaseServiceImpl<Long, TbPracticeExam> {
    @Resource
    private TbPracticeExamDaoImpl tbPracticeExamDaoImpl;

    @Override
    protected BaseDaoImpl<Long, TbPracticeExam> getBaseDaoImpl() {
        return tbPracticeExamDaoImpl;
    }
}