package com.yisi.stiku.basedata.rpc.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yisi.stiku.basedata.entity.TblAuthUser;

/**
 * 
 * @author shangfeng
 *
 */
public interface UserRpcService {

	public final static String EXIST_ERROR_CODE = "ALREADY_EXISTS";

	/**
	 * 
	 * @param userInfo
	 * @return
	 */
	boolean update(TblAuthUser userInfo);

	/**
	 * 
	 * @param userId
	 * @param loginIP
	 * @param userType
	 * @return
	 */
	boolean updateLoginInfo(long userId, int userType, String token, String loginIP);

	/**
	 * 
	 * @param userId
	 * @param token
	 * @return
	 */
	boolean logout(long userId, String token);

	/**
	 * 
	 * @param userInfo
	 * @return
	 */
	boolean save(TblAuthUser userInfo);

	/**
	 * 
	 * @param userId
	 * @param roleIds
	 * @return 更新用户与角色的关系。更新成功返回true；否则返回false
	 */
	boolean updateUser2Role(long userId, String[] roleIds);

	/**
	 * 判断是否为首次登陆
	 * 
	 * @param userId
	 * @return
	 */
	boolean isFirstLogon(long userId);

	/**
	 * 
	 * @param userId
	 * @return 根据多个用户ID，查询出对应的用户信息
	 */
	List<TblAuthUser> findUsers(Long... userIds);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	TblAuthUser getUserInfoById(long userId);

	/**
	 * 
	 * @param email
	 * @return
	 */
	TblAuthUser getUserInfoByEmail(String email);

	/**
	 * 
	 * @param mobile
	 * @return
	 */
	TblAuthUser getUserInfoByMobile(String mobile);

	/**
	 * 
	 * @param signName
	 * @return
	 */
	TblAuthUser getUserInfoBySignName(String signName);

	/**
	 * 
	 * @param authUser
	 *            携带user属性查询条件的对象
	 * @param keyword
	 *            关键字，可以根据用户名、姓名、昵称、邮箱 或者 手机号进行模糊查询
	 * @param currPage
	 *            当前页码
	 * @param maxRows
	 *            每页的最大的显示行数
	 * @return 返回符合条件的用户列表
	 */
	Page<TblAuthUser> searchUser(TblAuthUser authUser, String keyword,
			int currPage, int maxRows);

	/**
	 * 
	 * @param authUser
	 *            携带user属性查询条件的对象
	 * @param users
	 *            过滤users
	 * @param keyword
	 *            关键字，可以根据用户名、姓名、昵称、邮箱 或者 手机号进行模糊查询
	 * @param currPage
	 *            当前页码
	 * @param maxRows
	 *            每页的最大的显示行数
	 * @return 返回符合条件的用户列表
	 */
	Page<TblAuthUser> searchUserByFilterUsers(TblAuthUser authUser,
			List<Long> users, String keyword, int currPage, int maxRows);

	/**
	 * 
	 * @param userId
	 * @param password
	 * @return
	 */
	boolean resetPasswd(long userId, String password);

	/**
	 * 
	 * @param userId
	 * @param password
	 * @return
	 */
	boolean checkPasswd(long userId, String password);

	/**
	 * 
	 * @param loginName
	 * @param password
	 * @return 如果密码校验通过，则返回对应的user对象；否认将抛出运行时异常 BaseRuntimeException
	 */
	TblAuthUser checkPasswd(String loginName, String password);

	/**
	 * 
	 * @param cardPrefix
	 * @return
	 */
	String findMaxIdByPrefix(String cardPrefix);

	/**
	 * 
	 * @param userId
	 * @return 如果指定的用户可以被启用，则返回true，否则返回false
	 */
	public boolean canResetNormal(long userId);
}
