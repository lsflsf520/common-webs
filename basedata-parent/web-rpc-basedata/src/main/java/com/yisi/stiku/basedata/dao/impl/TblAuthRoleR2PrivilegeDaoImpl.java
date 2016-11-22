package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TblAuthRoleR2PrivilegeDao;
import com.yisi.stiku.basedata.entity.TblAuthRoleR2Privilege;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TblAuthRoleR2PrivilegeDaoImpl extends BaseDaoImpl<String, TblAuthRoleR2Privilege> {
    @Resource
    private TblAuthRoleR2PrivilegeDao tblAuthRoleR2PrivilegeDao;

    @Override
    protected BaseDao<String, TblAuthRoleR2Privilege> getProxyBaseDao() {
        return tblAuthRoleR2PrivilegeDao;
    }
}