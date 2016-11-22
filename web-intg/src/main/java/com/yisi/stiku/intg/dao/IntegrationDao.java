package com.yisi.stiku.intg.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.intg.entity.Integration;

@Repository
public interface IntegrationDao extends BaseDao<Integer, Integration> {

	public void resolveConflict(@Param("intgId") int intgId);

}