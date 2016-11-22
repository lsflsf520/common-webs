package com.yisi.stiku.wallet.rpc.service;

import com.yisi.stiku.wallet.entity.FuncCard;

/**
 * 
 * @author shangfeng
 *
 */
public interface FuncCardRpcService {

	/**
	 * @param userId 用户的id
	 * @return 返回当前登录用户的最高级别的功能卡
	 */
	public FuncCard loadByUserId(long userId);
	
	/**
	 * 
	 * @param actCode 激活码
	 * @return 如果激活发生异常则将抛出BaseRuntimeException异常
	 */
//	public void actFuncCard(String actCode);
	
	/**
	 * @param userId 用户的id
	 * @param actCode 激活码
	 * @return 如果升级发生异常则将抛出BaseRuntimeException异常
	 *   
	 */
	public void upgrade(long userId, String actCode);
	
	/**
	 * 使用激活码续费
	 * @param userId
	 * @param actCode 
	 */
	public void continueFee(long userId, String actCode);
	
}
