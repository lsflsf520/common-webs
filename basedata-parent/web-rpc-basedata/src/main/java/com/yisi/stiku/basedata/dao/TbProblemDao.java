package com.yisi.stiku.basedata.dao;

import com.yisi.stiku.basedata.entity.TbProblem;
import com.yisi.stiku.db.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface TbProblemDao extends BaseDao<Long, TbProblem> {
    int updateByPrimaryKeyWithBLOBs(TbProblem record);
}