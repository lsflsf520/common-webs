package com.yisi.stiku.statdata.dao.impl;

import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.statdata.dao.ClassFenbanDataDao;
import com.yisi.stiku.statdata.entity.ClassFenbanData;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class ClassFenbanDataDaoImpl extends BaseDaoImpl<Integer, ClassFenbanData> {
    @Resource
    private ClassFenbanDataDao classFenbanDataDao;

    @Override
    protected BaseDao<Integer, ClassFenbanData> getProxyBaseDao() {
        return classFenbanDataDao;
    }
}