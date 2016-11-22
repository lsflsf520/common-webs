package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TblAuthUserR2RoleDao;
import com.yisi.stiku.basedata.entity.TblAuthUserR2Role;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TblAuthUserR2RoleDaoImpl extends BaseDaoImpl<String, TblAuthUserR2Role> {
    @Resource
    private TblAuthUserR2RoleDao tblAuthUserR2RoleDao;

    @Override
    protected BaseDao<String, TblAuthUserR2Role> getProxyBaseDao() {
        return tblAuthUserR2RoleDao;
    }
}