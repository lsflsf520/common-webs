package com.yisi.stiku.basedata.service.impl;

import com.yisi.stiku.basedata.dao.impl.TbExtendFinalRecordDaoImpl;
import com.yisi.stiku.basedata.entity.TbExtendFinalRecord;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TbExtendFinalRecordServiceImpl extends BaseServiceImpl<Long, TbExtendFinalRecord> {
    @Resource
    private TbExtendFinalRecordDaoImpl tbExtendFinalRecordDaoImpl;

    @Override
    protected BaseDaoImpl<Long, TbExtendFinalRecord> getBaseDaoImpl() {
        return tbExtendFinalRecordDaoImpl;
    }
}