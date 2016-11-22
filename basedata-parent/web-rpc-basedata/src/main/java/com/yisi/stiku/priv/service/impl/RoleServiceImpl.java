package com.yisi.stiku.priv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.yisi.stiku.basedata.entity.TblAuthUser;
import com.yisi.stiku.basedata.rpc.service.UserRpcService;
import com.yisi.stiku.common.bean.EntityState;
import com.yisi.stiku.common.bean.PageInfo.OrderDirection;
import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.common.utils.ArrayUtils;
import com.yisi.stiku.conf.ConfigOnZk;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.priv.dao.impl.RoleDaoImpl;
import com.yisi.stiku.priv.entity.Role;
import com.yisi.stiku.priv.entity.RoleR2Link;
import com.yisi.stiku.priv.entity.UserR2Role;
import com.yisi.stiku.priv.rpc.service.RoleMgrRpcService;
import com.yisi.stiku.rpc.annotation.RpcService;

@Service
@RpcService(RoleMgrRpcService.class)
public class RoleServiceImpl extends BaseServiceImpl<Integer, Role> implements
		RoleMgrRpcService {

	@Resource
	private RoleDaoImpl roleDaoImpl;

	@Resource
	private RoleR2LinkServiceImpl roleR2LinkServiceImpl;

	@Resource
	private UserR2RoleServiceImpl userR2RoleServiceImpl;

	@Resource
	private UserRpcService userRpcService;

	@Override
	protected BaseDaoImpl<Integer, Role> getBaseDaoImpl() {

		return roleDaoImpl;
	}

	@Override
	public boolean save(Role role) {

		if (role.getId() == null) {
			role.setDbState(EntityState.NORMAL);
			role.setLastUptime(new Date());
			role.setCreateTime(new Date());
			this.insert(role);
			return true;
		}

		return this.update(role);
	}

	@Override
	public boolean invalid(int id) {

		UserR2Role urr = new UserR2Role();
		urr.setRoleId(id);
		List<UserR2Role> urrList = userR2RoleServiceImpl.findByEntity(urr);
		if (urrList != null && !urrList.isEmpty()) {
			throw new BaseRuntimeException("NOT_SUPPORT",
					"该角色已经作用在某些用户上，不能被删除！");
		}
		Role role = new Role();
		role.setId(id);
		role.setDbState(EntityState.INVALID);
		return this.update(role);
	}

	@Override
	public List<Role> findByUserId(long userId) {

		return roleDaoImpl.findByUserId(userId);
	}

	@Override
	public List<Integer> findRoleIdByUserId(long userId) {

		UserR2Role urr = new UserR2Role();
		urr.setUserId(userId);
		List<UserR2Role> urrList = userR2RoleServiceImpl.findByEntity(urr);
		List<Integer> roleIds = new ArrayList<Integer>();
		if (urrList != null && !urrList.isEmpty()) {
			for (UserR2Role currUrr : urrList) {
				roleIds.add(currUrr.getRoleId());
			}
		}
		return roleIds;
	}

	@Override
	public List<Role> findAvailRoles() {

		Role role = new Role();
		role.setDbState(EntityState.NORMAL);
		return this.findByEntity(role, "name", OrderDirection.asc);
	}

	@Override
	public boolean saveRole2Links(int roleId, List<Integer> savelinkIds,
			String projectName) {

		Role role = this.findById(roleId);
		if (null == role) {
			return false;
		}

		roleR2LinkServiceImpl.deleteByProjectName(projectName, roleId);

		if (ArrayUtils.isEmpty(savelinkIds)) {
			return true;
		}

		RoleR2Link role2Link = new RoleR2Link();
		role2Link.setRoleId(roleId);
		// 查询当前关联Link
		List<RoleR2Link> currtRole2Link = roleR2LinkServiceImpl
				.findByEntity(role2Link);

		List<RoleR2Link> saveRole2Link = new ArrayList<RoleR2Link>();
		// saveRole2Link.addAll(currtRole2Link);

		for (RoleR2Link roleR2Link : currtRole2Link) {
			// 筛选出其他projectname的权限无需移除
			if (savelinkIds.contains(roleR2Link.getLinkId())) {
				savelinkIds.remove(roleR2Link.getLinkId());
			}
		}
		// 添加勾选的权限
		for (int lkId : savelinkIds) {
			RoleR2Link roleR2Link = new RoleR2Link();
			roleR2Link.setRoleId(roleId);
			roleR2Link.setLinkId(lkId);
			saveRole2Link.add(roleR2Link);
		}

		if (ArrayUtils.isEmpty(saveRole2Link)) {
			return true;
		}

		roleR2LinkServiceImpl.insertBatch(saveRole2Link);

		return true;
	}

	/**
	 * 
	 * @return 获取可以用于设置用户类型的角色
	 */
	public List<Role> findUserRole() {

		return roleDaoImpl.findUserRole();
	}

	@Override
	public boolean saveUser2Roles(long userId, List<Integer> roleIds) {

		userR2RoleServiceImpl.deleteByUserId(userId);

		if (roleIds != null && !roleIds.isEmpty()) {
			List<UserR2Role> urrList = new ArrayList<UserR2Role>();
			for (Integer roleId : roleIds) {
				UserR2Role urr = new UserR2Role();
				urr.setRoleId(roleId);
				urr.setUserId(userId);

				urrList.add(urr);
			}
			userR2RoleServiceImpl.insertBatch(urrList);
		}

		return true;
	}

	@Override
	public Page<Role> searchRole(int currPage, int maxRows) {

		Role role = new Role();
		role.setDbState(EntityState.NORMAL);
		return this.findByPage(role, currPage, maxRows, " order by name desc");
	}

	@Override
	public List<Long> findUsersByRoles(Integer roleId) {

		return userR2RoleServiceImpl.loadByRoleId(roleId);
	}

	@Override
	public List<TblAuthUser> findUserListByRole(int roleId) {

		List<Long> userIdList = findUsersByRoles(roleId);
		if (userIdList != null && !userIdList.isEmpty()) {
			return userRpcService.findUsers(userIdList.toArray(new Long[0]));
		}

		return new ArrayList<TblAuthUser>();
	}

	@Override
	public boolean isInRole(long userId, Integer... roleIds) {

		if (roleIds == null || roleIds.length <= 0) {
			return false;
		}

		List<Integer> userRoleIds = findRoleIdByUserId(userId);
		if (userRoleIds.isEmpty()) {
			return false;
		}

		for (int roleId : roleIds) {
			if (userRoleIds.contains(roleId)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isInRole(long userId, String... zkRoleKeys) {

		if (zkRoleKeys == null || zkRoleKeys.length <= 0) {
			return false;
		}
		List<Integer> roleIds = new ArrayList<Integer>();
		for (String roleKey : zkRoleKeys) {
			if (StringUtils.isNotBlank(roleKey)) {
				if (!roleKey.startsWith("user.role.")) {
					roleKey = "user.role." + roleKey;
				}

				int[] roleIdArr = ConfigOnZk.getIntArr("web-rpc-basedata/application.properties", roleKey);

				if (roleIdArr != null && roleIdArr.length > 0) {
					for (int roleId : roleIdArr) {
						roleIds.add(roleId);
					}
				}
			}
		}

		return isInRole(userId, roleIds.toArray(new Integer[0]));
	}
}