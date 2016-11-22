package com.yisi.stiku.basedata.service.impl;

import com.yisi.stiku.basedata.dao.impl.TbVideoZhenduanDaoImpl;
import com.yisi.stiku.basedata.entity.TbVideoZhenduan;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TbVideoZhenduanServiceImpl extends BaseServiceImpl<Long, TbVideoZhenduan> {
    @Resource
    private TbVideoZhenduanDaoImpl tbVideoZhenduanDaoImpl;

    @Override
    protected BaseDaoImpl<Long, TbVideoZhenduan> getBaseDaoImpl() {
        return tbVideoZhenduanDaoImpl;
    }
}