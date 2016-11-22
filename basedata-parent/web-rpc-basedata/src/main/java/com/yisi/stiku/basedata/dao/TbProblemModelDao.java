package com.yisi.stiku.basedata.dao;

import com.yisi.stiku.basedata.entity.TbProblemModel;
import com.yisi.stiku.db.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface TbProblemModelDao extends BaseDao<Long, TbProblemModel> {
    int updateByPrimaryKeyWithBLOBs(TbProblemModel record);
}