package com.yisi.stiku.statdata.service;

import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.statdata.dao.impl.ClassFenbanDataDaoImpl;
import com.yisi.stiku.statdata.entity.ClassFenbanData;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ClassFenbanDataServiceImpl extends BaseServiceImpl<Integer, ClassFenbanData> {
    @Resource
    private ClassFenbanDataDaoImpl classFenbanDataDaoImpl;

    @Override
    protected BaseDaoImpl<Integer, ClassFenbanData> getBaseDaoImpl() {
        return classFenbanDataDaoImpl;
    }
}