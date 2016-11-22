package com.yisi.stiku.basedata.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yisi.stiku.basedata.dao.TblAuthUserDao;
import com.yisi.stiku.basedata.entity.TblAuthUser;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;

@Repository
public class TblAuthUserDaoImpl extends BaseDaoImpl<Long, TblAuthUser> {

	@Resource
	private TblAuthUserDao tblAuthUserDao;

	@Override
	protected BaseDao<Long, TblAuthUser> getProxyBaseDao() {

		return tblAuthUserDao;
	}

	public void clearRoleByUserId(long userId) {

		this.getSqlSessionTemplate().delete(getMapperNamespace() + ".clearRoleByUserId", userId);
	}

	public void insertUser2Roles(long userId, String[] roleIds) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("roleIds", roleIds);
		this.getSqlSessionTemplate().insert(getMapperNamespace() + ".insertUser2Roles", paramMap);
	}

	public TblAuthUser exists(TblAuthUser authUser) {

		if (StringUtils.isBlank(authUser.getSignName()) && StringUtils.isBlank(authUser.getEmail())
				&& StringUtils.isBlank(authUser.getMobile())) {
			return null;
		}
		TblAuthUser user = this.getSqlSessionTemplate().selectOne(getMapperNamespace() + ".exists", authUser);

		return user;
	}

	public List<TblAuthUser> existEmailOrMobileForUserId(long userId) {

		return this.getSqlSessionTemplate().selectList(getMapperNamespace() + ".existEmailOrMobileForUserId", userId);
	}

	public List<TblAuthUser> findUsers(Long... userIds) {

		return this.getSqlSessionTemplate().selectList(getMapperNamespace() + ".findUsers", userIds);
	}

	public String findMaxIdByPrefix(String prefixText) {

		return this.getSqlSessionTemplate().selectOne(getMapperNamespace() + ".findMaxIdByPrefix", prefixText);
	}

	public boolean updateByPKForAdmin(TblAuthUser userInfo) {

		return this.getSqlSessionTemplate().update(getMapperNamespace() + ".updateByPKForAdmin", userInfo) > 0;
	}
}