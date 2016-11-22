package com.yisi.stiku.wallet.rpc.service;

import com.yisi.stiku.wallet.entity.ResourceInfo;
import com.yisi.stiku.wallet.rpc.constant.PayChannel;

public interface ResMgrRpcService {

	/**
	 * 
	 * @param resId 资源表的主键id(注意: 不是资源本身的id)
	 * @param userId 目标用户id，一般都为学生的id
	 * @param payChannel 付费渠道
	 * @param operUserId 操作者的id
	 * @param operUserName  操作者的用户名
	 * @param desc 绑定的原因
	 * @return 如果资源信息绑定成功，则返回true；否则返回false
	 */
	boolean bindRes2User(int resId, long userId, PayChannel payChannel, long operUserId, String operUserName, String desc);
	
	/**
	 * 
	 * @param resId 资源表的主键id(注意: 不是资源本身的id)
	 * @param userId 目标用户id，一般都为学生的id
	 * @param operUserId 操作者的id
	 * @param operUserName  操作者的用户名
	 * @param desc 解绑的描述
	 * @return 将某个学生的付费资源解绑
	 */
	boolean unbindRes2User(int resId, long userId, long operUserId, String operUserName, String desc);
	
	/**
	 * 
	 * @param resInfo 资源实体类
	 * @param operUserId 操作者的id
	 * @param operUserName  操作者的用户名
	 * @return 资源新增成功则返回true；否则返回false
	 */
	boolean addResInfo(ResourceInfo resInfo, long operUserId, String operUserName);
	
	/**
	 * 
	 * @param resId 资源表的主键id(注意: 不是资源本身的id)
	 * @param operUserId 操作者的id
	 * @param operUserName  操作者的用户名
	 * @param desc 失效掉该资源的原因
	 * @return 将某个资源失效掉, 操作成功则返回true；否则返回false
	 */
	boolean invalidRes(int resId, long operUserId, String operUserName, String desc);
	
}
