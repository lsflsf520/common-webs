package com.yisi.stiku.priv.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yisi.stiku.cache.constant.DefaultJedisKeyNS;
import com.yisi.stiku.cache.redis.ShardJedisTool;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.priv.dao.impl.RoleR2LinkDaoImpl;
import com.yisi.stiku.priv.entity.RoleR2Link;

@Service
public class RoleR2LinkServiceImpl extends BaseServiceImpl<Integer, RoleR2Link> {
	@Resource
	private RoleR2LinkDaoImpl roleR2LinkDaoImpl;

	@Override
	protected BaseDaoImpl<Integer, RoleR2Link> getBaseDaoImpl() {
		return roleR2LinkDaoImpl;
	}

	public boolean deleteByRrl(List<Integer> linkIds, List<Integer> roleIds) {
		if (DefaultJedisKeyNS.lk.needRemoveAllCacheAfterModify()) {
			ShardJedisTool.delHKey(DefaultJedisKeyNS.lk);
		}
		if (linkIds == null || linkIds.isEmpty() || roleIds == null
				|| roleIds.isEmpty()) {
			return false;
		}
		return roleR2LinkDaoImpl.deleteByRrl(linkIds, roleIds);
	}

	public boolean deleteByRole(Integer roleId) {
		if (roleId == null) {
			return false;
		}
		if (DefaultJedisKeyNS.lk.needRemoveAllCacheAfterModify()) {
			ShardJedisTool.delHKey(DefaultJedisKeyNS.lk);
		}
		return roleR2LinkDaoImpl.deleteByRoleId(roleId);
	}

	public boolean deleteByProjectName(String projectName, Integer roleId) {
		if (projectName == null) {
			return false;
		}

		if (DefaultJedisKeyNS.lk.needRemoveAllCacheAfterModify()) {
			ShardJedisTool.delHKey(DefaultJedisKeyNS.lk);
		}

		return roleR2LinkDaoImpl.deleteByProejctName(projectName, roleId);
	}
	// public boolean deleteByRoleId(int roleId, List<Integer> linkIds) {
	// if (DefaultCacheNS.SYS_PRIV.needRemoveAllCacheAfterModify()) {
	// EhCacheTool.removeAll(DefaultCacheNS.SYS_PRIV);
	// }
	// return roleR2LinkDaoImpl.deleteByRoleId(roleId, linkIds);
	// }

}
