package com.yisi.stiku.basedata.dao;

import com.yisi.stiku.basedata.entity.TblAuthUser;
import com.yisi.stiku.db.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface TblAuthUserDao extends BaseDao<Long, TblAuthUser> {
}