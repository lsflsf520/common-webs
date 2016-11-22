package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TbVideoZhenduanDao;
import com.yisi.stiku.basedata.entity.TbVideoZhenduan;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TbVideoZhenduanDaoImpl extends BaseDaoImpl<Long, TbVideoZhenduan> {
    @Resource
    private TbVideoZhenduanDao tbVideoZhenduanDao;

    @Override
    protected BaseDao<Long, TbVideoZhenduan> getProxyBaseDao() {
        return tbVideoZhenduanDao;
    }
}