package com.yisi.stiku.statdata.dao.impl;

import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.statdata.dao.StudentFenbanDataDao;
import com.yisi.stiku.statdata.entity.StudentFenbanData;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class StudentFenbanDataDaoImpl extends BaseDaoImpl<Integer, StudentFenbanData> {
    @Resource
    private StudentFenbanDataDao studentFenbanDataDao;

    @Override
    protected BaseDao<Integer, StudentFenbanData> getProxyBaseDao() {
        return studentFenbanDataDao;
    }
}