package com.yisi.stiku.msg.dao.impl;

import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.msg.dao.EmailTmplDao;
import com.yisi.stiku.msg.entity.EmailTmpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class EmailTmplDaoImpl extends BaseDaoImpl<Integer, EmailTmpl> {
    @Resource
    private EmailTmplDao emailTmplDao;

    @Override
    protected BaseDao<Integer, EmailTmpl> getProxyBaseDao() {
        return emailTmplDao;
    }
}