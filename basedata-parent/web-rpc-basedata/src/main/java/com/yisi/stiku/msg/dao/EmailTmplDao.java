package com.yisi.stiku.msg.dao;

import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.msg.entity.EmailTmpl;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTmplDao extends BaseDao<Integer, EmailTmpl> {
    int updateByPrimaryKeyWithBLOBs(EmailTmpl record);
}