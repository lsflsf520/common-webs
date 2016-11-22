package com.yisi.stiku.basedata.rpc.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yisi.stiku.basedata.entity.TblAuthRole;
import com.yisi.stiku.basedata.rpc.bean.RoleInfo;

public interface RoleRpcService {

	/**
	 * 
	 * @param userId
	 * @return 返回用户所拥有的角色
	 */
	List<RoleInfo> findEnabledRolesByUserId(long userId);
	
	/**
	 * 
	 * @param role 
	 * @return 根据role的id是否为空来判断是insert还是update
	 */
	boolean save(TblAuthRole role);
	
	/**
	 * 
	 * @param currPage
	 * @param maxRows
	 * @return
	 */
	Page<TblAuthRole> searchRole(int currPage, int maxRows);
}
