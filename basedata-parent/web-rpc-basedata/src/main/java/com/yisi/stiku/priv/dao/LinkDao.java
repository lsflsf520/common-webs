package com.yisi.stiku.priv.dao;

import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.priv.entity.Link;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkDao extends BaseDao<Integer, Link> {
}