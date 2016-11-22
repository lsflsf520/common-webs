package com.yisi.stiku.wallet.rpc.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.PageImpl;

import com.yisi.stiku.common.bean.PagerControl;
import com.yisi.stiku.wallet.entity.TradeLog;
import com.yisi.stiku.wallet.rpc.constant.TradeType;

/**
 * 
 * @author shangfeng
 *
 */
public interface TradeLogRpcService {

	/**
	 * 
	 * @param userId
	 * @param page
	 * @param maxRow
	 * @return
	 */
	PageImpl<TradeLog> findTradeLogByUserId(long userId, TradeType tradeType, Date startTime, Date endTime, int page, int maxRow);
	
	/**
	 * 
	 * @param userId
	 * @param tradeType
	 * @param lastJiesuanEndTime
	 * @param page
	 * @param maxRow
	 * @return
	 */
	PageImpl<TradeLog> findTradeLogByUserId(long userId, TradeType tradeType, Date lastJiesuanEndTime, int page, int maxRow);

	PageImpl<TradeLog> findYyeDetailByUserId(long userId, Date startTime, Date endTime, int page, int maxRow);
	
	PageImpl<TradeLog> findYyeDetailByUserId(long userId, Date lastJiesuanEndTime, int page, int maxRow);
	
	List<TradeLog> loadJiesuanDetail(Date startTime, Date endTime, long agentUserId);
	
}
