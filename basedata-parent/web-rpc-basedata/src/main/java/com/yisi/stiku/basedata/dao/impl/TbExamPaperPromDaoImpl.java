package com.yisi.stiku.basedata.dao.impl;

import com.yisi.stiku.basedata.dao.TbExamPaperPromDao;
import com.yisi.stiku.basedata.entity.TbExamPaperProm;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class TbExamPaperPromDaoImpl extends BaseDaoImpl<Long, TbExamPaperProm> {
    @Resource
    private TbExamPaperPromDao tbExamPaperPromDao;

    @Override
    protected BaseDao<Long, TbExamPaperProm> getProxyBaseDao() {
        return tbExamPaperPromDao;
    }
}