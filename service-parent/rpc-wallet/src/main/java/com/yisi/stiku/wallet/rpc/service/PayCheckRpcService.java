package com.yisi.stiku.wallet.rpc.service;

import com.yisi.stiku.wallet.rpc.constant.ResType;

/**
 * 检测用户是否能访问某个付费资源
 * @author shangfeng
 *
 */
public interface PayCheckRpcService {

	/**
	 * 
	 * @param userId 权限检测的目标用户ID，一般都为学生id
	 * @param resId 付费资源的标识
	 * @param resType 付费资源的类型
	 * @return 如果有权限，则返回true；否则返回false
	 */
	boolean hasAccessAuth(long userId, String resId, ResType resType);
	
	/**
	 * 
	 * @param userId 权限检测的目标用户ID，一般都为学生id
	 * @param resourceInfoId 付费资源在 resource_info表中的id值
	 * @return 
	 */
	boolean hasAccessAuth(long userId, int resourceInfoId);
	
	/**
	 * 
	 * @param userId
	 * @param resourceInfoId
	 * @return 返回用户拥有指定资源的截止时间，以毫秒为单位。如果用户没有该资源的权限，则返回-1
	 */
	long getResEndTime(long userId, int resourceInfoId);
	
	/**
	 * 
	 * @param userId 目标用户ID，一般都为学生id
	 * @param resId 付费资源的标识
	 * @param resType 付费资源的类型
	 * @return
	 */
	boolean countDown(long userId, String resId, ResType resType);
	
	/**
	 * 
	 * @param userId 目标用户ID，一般都为学生id
	 * @param resourceInfoId 付费资源在 resource_info表中的id值
	 * @return
	 */
	boolean countDown(long userId, int resourceInfoId);
}
