package com.yisi.stiku.basedata.service.impl;

import com.yisi.stiku.basedata.dao.impl.TblAuthDepartmentDaoImpl;
import com.yisi.stiku.basedata.entity.TblAuthDepartment;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TblAuthDepartmentServiceImpl extends BaseServiceImpl<String, TblAuthDepartment> {
    @Resource
    private TblAuthDepartmentDaoImpl tblAuthDepartmentDaoImpl;

    @Override
    protected BaseDaoImpl<String, TblAuthDepartment> getBaseDaoImpl() {
        return tblAuthDepartmentDaoImpl;
    }
}