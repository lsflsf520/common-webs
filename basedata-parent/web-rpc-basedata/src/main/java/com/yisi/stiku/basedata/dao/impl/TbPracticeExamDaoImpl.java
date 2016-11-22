package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TbPracticeExamDao;
import com.yisi.stiku.basedata.entity.TbPracticeExam;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TbPracticeExamDaoImpl extends BaseDaoImpl<Long, TbPracticeExam> {
    @Resource
    private TbPracticeExamDao tbPracticeExamDao;

    @Override
    protected BaseDao<Long, TbPracticeExam> getProxyBaseDao() {
        return tbPracticeExamDao;
    }
}