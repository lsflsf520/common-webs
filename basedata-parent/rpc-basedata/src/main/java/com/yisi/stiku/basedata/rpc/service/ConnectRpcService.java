package com.yisi.stiku.basedata.rpc.service;

import java.util.List;

import com.yisi.stiku.basedata.entity.TblConnectUser;
import com.yisi.stiku.basedata.rpc.constant.ThirdLoginType;

/**
 * 第三方登录信息管理接口
 * @author shangfeng
 *
 */
public interface ConnectRpcService {

	/**
	 * 
	 * @param connUser
	 * @return 在第三方认证成功之后，存储第三方登录信息，并返回connUser的id
	 */
	public int saveConnectUser(TblConnectUser connUser);
	
	/**
	 * 
	 * @param userId
	 * @param loginType
	 * @return 解绑与某个第三方登录的关系
	 */
	public boolean unbind(long userId, ThirdLoginType loginType);
	
	/**
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	public boolean bindUser(int id, long userId);
	
	/**
	 * 
	 * @param openId
	 * @param loginType
	 * @return 返回指定的第三方用户信息，即使状态不正常也需要返回
	 */
	public TblConnectUser loadConnectUser(String openId, ThirdLoginType loginType);
	
	/**
	 * 
	 * @param userId
	 * @param loginType
	 * @return 返回指定的第三方用户信息，只返回状态正常的对象
	 */
	public TblConnectUser loadConnectUser(long userId, ThirdLoginType loginType);
	
	/**
	 * 
	 * @param userId
	 * @return 返回某个17大学的用户绑定的第三方用户信息，只返回状态正常的对象
	 */
	public List<TblConnectUser> loadConnectUsers(long userId);
	
	/**
	 * 
	 * @param id
	 * @return 根据数据库记录的id，返回第三方用户信息，即使状态不正常也需要返回
	 */
	public TblConnectUser findById(Integer id);
}
