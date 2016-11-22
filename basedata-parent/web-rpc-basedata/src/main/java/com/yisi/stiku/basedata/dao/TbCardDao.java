package com.yisi.stiku.basedata.dao;

import com.yisi.stiku.basedata.entity.TbCard;
import com.yisi.stiku.db.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface TbCardDao extends BaseDao<String, TbCard> {
}