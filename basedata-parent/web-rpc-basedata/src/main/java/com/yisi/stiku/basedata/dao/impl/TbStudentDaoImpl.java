package com.yisi.stiku.basedata.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yisi.stiku.basedata.dao.TbStudentDao;
import com.yisi.stiku.basedata.entity.TbStudent;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;

@Repository
public class TbStudentDaoImpl extends BaseDaoImpl<Long, TbStudent> {

	@Resource
	private TbStudentDao tbStudentDao;

	@Override
	protected BaseDao<Long, TbStudent> getProxyBaseDao() {

		return tbStudentDao;
	}

	public List<TbStudent> findListByStudentIds(Long... studentIds) {

		return this.getSqlSessionTemplate().selectList(getMapperNamespace() + ".findListByStudentIds", studentIds);
	}

	public List<TbStudent> getStudentByClassId(long classId) {

		return tbStudentDao.getStudentByClassId(classId);
	}
}