package com.yisi.stiku.priv.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.priv.dao.impl.UserR2RoleDaoImpl;
import com.yisi.stiku.priv.entity.UserR2Role;

@Service
public class UserR2RoleServiceImpl extends BaseServiceImpl<Integer, UserR2Role> {
	@Resource
	private UserR2RoleDaoImpl userR2RoleDaoImpl;

	@Override
	protected BaseDaoImpl<Integer, UserR2Role> getBaseDaoImpl() {
		return userR2RoleDaoImpl;
	}

	public boolean deleteByUserId(long userId) {
		return userR2RoleDaoImpl.deleteByUserId(userId);
	}

	public boolean deleteByRoleId(int roleId) {
		return userR2RoleDaoImpl.deleteByRoleId(roleId);
	}

	public boolean exists(long userId, int roleId) {
		return userR2RoleDaoImpl.exist(userId, roleId);
	}

	public boolean deleteByUserAndRole(long userId, int roleId) {
		return userR2RoleDaoImpl.deleteByUserAndRole(userId, roleId);
	}

	public List<Integer/* roleId */> loadByUserId(long userId) {
		UserR2Role queryUrr = new UserR2Role();
		queryUrr.setUserId(userId);

		List<Integer/* roleId */> roleIds = new ArrayList<Integer>();
		List<UserR2Role> urrList = userR2RoleDaoImpl.findByEntity(queryUrr);
		if (urrList != null && !urrList.isEmpty()) {
			for (UserR2Role urr : urrList) {
				roleIds.add(urr.getRoleId());
			}
		}
		roleIds.add(2); // 默认给每个用户添加匿名角色(数据库中的匿名角色的id为2)，即每个登录用户将拥有匿名角色下的所有权限
		return roleIds;
	}

	public List<Long/* userids */> loadByRoleId(int roleId) {
		UserR2Role queryUrr = new UserR2Role();
		queryUrr.setRoleId(roleId);

		List<Long/* userids */> userIds = new ArrayList<Long>();
		List<UserR2Role> urrList = userR2RoleDaoImpl.findByEntity(queryUrr);
		if (urrList != null && !urrList.isEmpty()) {
			for (UserR2Role urr : urrList) {
				userIds.add(urr.getUserId());
			}
		}

		return userIds;
	}

}