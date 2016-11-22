package com.yisi.stiku.basedata.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yisi.stiku.basedata.dao.TbtTeacherDao;
import com.yisi.stiku.basedata.entity.TbtTeacher;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;

@Repository
public class TbtTeacherDaoImpl extends BaseDaoImpl<Long, TbtTeacher> {

	@Resource
	private TbtTeacherDao tbtTeacherDao;

	@Override
	protected BaseDao<Long, TbtTeacher> getProxyBaseDao() {

		return tbtTeacherDao;
	}

	/**
	 * 
	 * @param teacher
	 * @return
	 */
	public boolean updateByUserId(TbtTeacher teacher) {

		return this.getSqlSessionTemplate().update(getMapperNamespace() + ".updateByUserId", teacher) >= 0;
	}

	public boolean clearSchoolId(long userId) {

		return this.getSqlSessionTemplate().update(getMapperNamespace() + ".clearSchoolId", userId) >= 0;
	}

	public List<TbtTeacher> findBySchoolIdAndSubjectId(Long schoolId,
			Long subjectId) {

		Map<String, Long> paramMap = new HashMap<String, Long>();
		paramMap.put("schoolId", schoolId);
		paramMap.put("subjectId", subjectId);

		return this.getSqlSessionTemplate().selectList(getMapperNamespace() + ".findBySchoolIdAndSubjectId", paramMap);
	}
}