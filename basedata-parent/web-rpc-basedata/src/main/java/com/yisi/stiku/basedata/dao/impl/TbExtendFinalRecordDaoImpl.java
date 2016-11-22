package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TbExtendFinalRecordDao;
import com.yisi.stiku.basedata.entity.TbExtendFinalRecord;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TbExtendFinalRecordDaoImpl extends BaseDaoImpl<Long, TbExtendFinalRecord> {
    @Resource
    private TbExtendFinalRecordDao tbExtendFinalRecordDao;

    @Override
    protected BaseDao<Long, TbExtendFinalRecord> getProxyBaseDao() {
        return tbExtendFinalRecordDao;
    }
}