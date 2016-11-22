package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TbExamPointDao;
import com.yisi.stiku.basedata.entity.TbExamPoint;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TbExamPointDaoImpl extends BaseDaoImpl<Long, TbExamPoint> {
    @Resource
    private TbExamPointDao tbExamPointDao;

    @Override
    protected BaseDao<Long, TbExamPoint> getProxyBaseDao() {
        return tbExamPointDao;
    }
}