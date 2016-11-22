package com.yisi.stiku.wallet.rpc.service;

import java.util.List;

import com.yisi.stiku.wallet.entity.JiesuanData;
import com.yisi.stiku.wallet.entity.ResourceInfo;
import com.yisi.stiku.wallet.entity.Wallet;
import com.yisi.stiku.wallet.rpc.constant.PayChannel;
import com.yisi.stiku.wallet.rpc.constant.TradeType;

/**
 * 钱包的查看和增减服务
 * @author shangfeng
 *
 */
public interface WalletRpcService {
	
	/**
	 * 
	 * @param userId
	 * @return 返回某个人的钱包信息
	 */
	Wallet getWalletByUserId(long userId);
	
	/**
	 * 
	 * @param userIds
	 * @return
	 */
	List<Wallet> getWalletList(Long... userIds);
	
	/**
	 * @param srcUserId 发生交易的源用户id，比如代理商给学生充值，那么这个字段记录的是代理商的id
	 * @param targetUserId 发生交易的目标用户id
	 * @param money 欲增加的金额，以分为单位
	 * @param tradeType 交易的类型，此处可以为 CHARGE、INCOME
	 * @param payChannel 付费渠道
	 * @param desc 交易描述信息
	 * @return 操作成功，返回true；否则返回false
	 */
	boolean consume(long srcUserId, long targetUserId, int money, TradeType tradeType, PayChannel payChannel, ResourceInfo resInfo);
	
	/**
	 * 
	 * @param srcUserId
	 * @param targetUserId
	 * @param money
	 * @param tradeType
	 * @param payChannel
	 * @return
	 */
	boolean chargeBalance(long srcUserId, long targetUserId, int money, TradeType tradeType, PayChannel payChannel);
	
	/**
	 * 
	 * @param operUserId 一般为财务人员的ID
	 * @param hasJiesuanData 目标用户ID
	 * @return 返回提现之后的结果
	 */
	boolean convertCash(long operUserId, JiesuanData hasJiesuanData);

	/**
	 * 
	 * @param srcUserId 要被扣减金额的用户id
	 * @param targetUserId 金额的受让者id
	 * @param money 转让的金额，以分为单位
	 * @param desc 交易的描述信息
	 * @return  操作成功，返回true；否则返回false
	 */
//	ResultModel transferBalance(long  srcUserId, long targetUserId, int money, String desc);
	
	/**
	 * 
	 * @param srcUserId 发生交易的源用户id，比如代理商给学生充值，那么这个字段记录的是代理商的id
	 * @param targetUserId 发生交易的目标用户id
	 * @param money 欲扣减的金额，以分为单位
	 * @param desc 交易的描述信息
	 * @return  操作成功，返回true；否则返回false
	 */
//	ResultModel minusBalance(long srcUserId, long targetUserId, int money, String desc);
	
	/**
	 * 
	 * @param srcUserId 发生交易的源用户id，比如代理商给学生充值，那么这个字段记录的是代理商的id
	 * @param targetUserId 发生交易的目标用户id
	 * @param pointNum 欲增加的活跃度点数,均为正整数
	 * @param tradeType 交易的类型，此处可以为 SYS_PROVIDE
	 * @param desc 交易的描述信息
	 * @return  操作成功，返回true；否则返回false
	 */
	boolean addPoint(long srcUserId, long targetUserId, int pointNum, TradeType tradeType, String desc);

	/**
	 * 
	 * @param srcUserId 要被扣减点数的用户id
	 * @param targetUserId 点数的受让者id
	 * @param pointNum 转让的活跃度点数,均为正整数
	 * @param desc 交易的描述信息
	 * @return  操作成功，返回true；否则返回false
	 */
//	ResultModel transferPoint(long  srcUserId, long targetUserId, int pointNum, String desc);
	
	/**
	 * 
	 * @param srcUserId 发生交易的源用户id，比如代理商给学生充值，那么这个字段记录的是代理商的id
	 * @param targetUserId 发生交易的目标用户id
	 * @param pointNum 欲扣减的点数，均为正整数
	 * @param desc 交易的描述信息
	 * @return  操作成功，返回true；否则返回false
	 */
//	ResultModel minusPoint(long srcUserId, long targetUserId, int pointNum, String desc);
	
}
