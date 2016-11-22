package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TblAuthDepartmentDao;
import com.yisi.stiku.basedata.entity.TblAuthDepartment;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TblAuthDepartmentDaoImpl extends BaseDaoImpl<String, TblAuthDepartment> {
    @Resource
    private TblAuthDepartmentDao tblAuthDepartmentDao;

    @Override
    protected BaseDao<String, TblAuthDepartment> getProxyBaseDao() {
        return tblAuthDepartmentDao;
    }
}