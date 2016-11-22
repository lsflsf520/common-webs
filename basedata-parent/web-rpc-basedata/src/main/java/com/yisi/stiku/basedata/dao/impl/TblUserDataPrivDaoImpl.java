package com.yisi.stiku.basedata.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yisi.stiku.basedata.dao.TblUserDataPrivDao;
import com.yisi.stiku.basedata.entity.TbTeacherLinkCls;
import com.yisi.stiku.basedata.entity.TblUserDataPriv;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;

@Repository
public class TblUserDataPrivDaoImpl extends BaseDaoImpl<Integer, TblUserDataPriv> {

	@Resource
	private TblUserDataPrivDao tblUserDataPrivDao;

	@Override
	protected BaseDao<Integer, TblUserDataPriv> getProxyBaseDao() {

		return tblUserDataPrivDao;
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public boolean deleteByUserId(long userId) {

		Map<String, Long> paramMap = new HashMap<String, Long>();
		paramMap.put("userId", userId);

		int effectRows = getSqlSessionTemplate().delete(getMapperNamespace() + ".deleteByUserId", paramMap);

		return effectRows > 0;
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<Long> findAllUserIdByCoach(long userId) {

		return getSqlSessionTemplate().selectList(getMapperNamespace() + ".findAllUserIdByCoach", userId);
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<Long> findAllUserIdByCoach(long userId, long shcoolId) {

		Map<String, Long> paramter = new HashMap<String, Long>();
		paramter.put("userId", userId);
		paramter.put("schoolId", shcoolId);
		return getSqlSessionTemplate().selectList(getMapperNamespace() + ".findAllUserIdByCoachSchool", paramter);
	}

	public List<Long> filterUserByType(long userId, int userType,
			Long... userIds) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("userType", userType);
		paramMap.put("userIds", userIds);

		return getSqlSessionTemplate().selectList(getMapperNamespace() + ".filterUserByType", paramMap);
	}

	public List<Long> filterUserBySchool(Long schoolId,
			Long... userIds) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("schoolId", schoolId);
		paramMap.put("userIds", userIds);

		return getSqlSessionTemplate().selectList(getMapperNamespace() + ".filterTeacherBySchool", paramMap);
	}

	public List<TbTeacherLinkCls> findTeachersLinkClasses(Long schoolId, List<Long> teachers) {

		Long[] teachersLong = teachers.toArray(new Long[teachers.size()]);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("schoolId", schoolId);
		paramMap.put("userIds", teachersLong);

		return getSqlSessionTemplate().selectList(getMapperNamespace() + ".findTeachersLinkClasses",
				paramMap);

	}

	public List<Long> findLinkClasses(Long schoolId) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("schoolid", schoolId);

		return getSqlSessionTemplate().selectList(getMapperNamespace() + ".findLinkClasses",
				paramMap);

	}
}