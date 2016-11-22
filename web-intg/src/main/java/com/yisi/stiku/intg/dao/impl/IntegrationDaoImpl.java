package com.yisi.stiku.intg.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.intg.dao.IntegrationDao;
import com.yisi.stiku.intg.entity.Integration;

@Repository
public class IntegrationDaoImpl extends BaseDaoImpl<Integer, Integration> {

	@Resource
	private IntegrationDao integrationDao;

	@Override
	protected BaseDao<Integer, Integration> getProxyBaseDao() {

		return integrationDao;
	}
}