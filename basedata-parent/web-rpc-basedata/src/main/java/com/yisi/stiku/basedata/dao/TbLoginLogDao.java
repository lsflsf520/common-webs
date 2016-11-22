package com.yisi.stiku.basedata.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yisi.stiku.basedata.entity.TbLoginLog;
import com.yisi.stiku.db.dao.BaseDao;

@Repository
public interface TbLoginLogDao extends BaseDao<String, TbLoginLog> {

	public int getLogonCnt(@Param("userId") long userId);

}