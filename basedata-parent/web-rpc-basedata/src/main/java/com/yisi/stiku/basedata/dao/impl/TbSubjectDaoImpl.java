package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TbSubjectDao;
import com.yisi.stiku.basedata.entity.TbSubject;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TbSubjectDaoImpl extends BaseDaoImpl<Long, TbSubject> {
    @Resource
    private TbSubjectDao tbSubjectDao;

    @Override
    protected BaseDao<Long, TbSubject> getProxyBaseDao() {
        return tbSubjectDao;
    }
}