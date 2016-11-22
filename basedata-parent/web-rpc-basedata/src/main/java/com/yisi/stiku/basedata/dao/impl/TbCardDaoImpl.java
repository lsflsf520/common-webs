package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TbCardDao;
import com.yisi.stiku.basedata.entity.TbCard;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TbCardDaoImpl extends BaseDaoImpl<String, TbCard> {
    @Resource
    private TbCardDao tbCardDao;

    @Override
    protected BaseDao<String, TbCard> getProxyBaseDao() {
        return tbCardDao;
    }
}