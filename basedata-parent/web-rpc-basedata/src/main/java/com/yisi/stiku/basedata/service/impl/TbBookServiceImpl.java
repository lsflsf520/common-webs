package com.yisi.stiku.basedata.service.impl;

import com.yisi.stiku.basedata.dao.impl.TbBookDaoImpl;
import com.yisi.stiku.basedata.entity.TbBook;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TbBookServiceImpl extends BaseServiceImpl<Long, TbBook> {
    @Resource
    private TbBookDaoImpl tbBookDaoImpl;

    @Override
    protected BaseDaoImpl<Long, TbBook> getBaseDaoImpl() {
        return tbBookDaoImpl;
    }
}