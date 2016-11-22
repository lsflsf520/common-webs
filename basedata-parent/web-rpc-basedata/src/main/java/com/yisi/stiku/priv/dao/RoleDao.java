package com.yisi.stiku.priv.dao;

import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.priv.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends BaseDao<Integer, Role> {
}