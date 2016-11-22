package com.yisi.stiku.basedata.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yisi.stiku.basedata.dao.TbClassDao;
import com.yisi.stiku.basedata.entity.TbClass;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;

@Repository
public class TbClassDaoImpl extends BaseDaoImpl<Long, TbClass> {

	@Resource
	private TbClassDao tbClassDao;

	@Override
	protected BaseDao<Long, TbClass> getProxyBaseDao() {

		return tbClassDao;
	}

	public List<TbClass> findClassList(List<Long> classIdList) {

		return this.getSqlSessionTemplate().selectList(getMapperNamespace() + ".findClassList", classIdList);
	}

	public List<TbClass> getNameBytype(String name, int type, int gradeYear, long schoolId) {

		return tbClassDao.getNameBytype(name, type, gradeYear, schoolId);
	}
}