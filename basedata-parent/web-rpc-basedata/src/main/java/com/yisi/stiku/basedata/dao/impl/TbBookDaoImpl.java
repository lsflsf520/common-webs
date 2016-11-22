package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TbBookDao;
import com.yisi.stiku.basedata.entity.TbBook;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TbBookDaoImpl extends BaseDaoImpl<Long, TbBook> {
    @Resource
    private TbBookDao tbBookDao;

    @Override
    protected BaseDao<Long, TbBook> getProxyBaseDao() {
        return tbBookDao;
    }
}