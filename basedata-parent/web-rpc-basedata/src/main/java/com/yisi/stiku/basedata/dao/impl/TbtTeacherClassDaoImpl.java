package com.yisi.stiku.basedata.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yisi.stiku.basedata.dao.TbtTeacherClassDao;
import com.yisi.stiku.basedata.entity.TbtTeacherClass;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;

@Repository
public class TbtTeacherClassDaoImpl extends BaseDaoImpl<Long, TbtTeacherClass> {

	@Resource
	private TbtTeacherClassDao tbtTeacherClassDao;

	@Override
	protected BaseDao<Long, TbtTeacherClass> getProxyBaseDao() {

		return tbtTeacherClassDao;
	}

	public List<TbtTeacherClass> findByTeacherId(Long teacherId) {

		TbtTeacherClass t = new TbtTeacherClass();
		t.setTeacherId(teacherId);
		return this.findByEntity(t);
	}

	public Long findTeacherIdByUserId(Long userId) {

		return getSqlSessionTemplate().selectOne(getMapperNamespace() + ".findTeacherIdByUserId",
				userId);
	}

	public List<TbtTeacherClass> findBySchoolId(Long schoolId) {

		TbtTeacherClass t = new TbtTeacherClass();
		t.setSchoolId(schoolId);
		return this.findByEntity(t);
	}

	public void deleteByTeacherIdAndClassId(Long teacherId, List<Long> classIds) {

		tbtTeacherClassDao.deleteByTeacherIdAndClassIds(teacherId, classIds);
	}

	public void deleteByTeacherId(Long teacherId) {

		tbtTeacherClassDao.deleteByTeacherId(teacherId);
	}

}