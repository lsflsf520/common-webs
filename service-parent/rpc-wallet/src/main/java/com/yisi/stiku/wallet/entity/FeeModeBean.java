package com.yisi.stiku.wallet.entity;

import com.yisi.stiku.common.utils.DateUtil;
import com.yisi.stiku.wallet.rpc.constant.FeeMode;

/**
 * 
 * @author shangfeng
 *
 */
public class FeeModeBean {

	private FeeMode feeMode;
	private String cardTypeName; //如果是激活卡模式，则会有卡类型
	private long endDate; //如果是激活卡模式，则会有截止日期
	
	public FeeModeBean(FeeMode feeMode){
		this.feeMode = feeMode;
	}
	
	public FeeModeBean(FeeMode feeMode, String cardTypeName, long endDate){
		this(feeMode);
		this.cardTypeName = cardTypeName;
		this.endDate = endDate;
	}

	public FeeMode getFeeMode() {
		return feeMode;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public long getEndDateMillis() {
		return endDate;
	}
	
	public boolean is1800Mode(){
		return this.feeMode != null && this.feeMode.is1800Mode();
	}
	
	public boolean isZizhuPrint(){
		return this.feeMode != null && this.feeMode.isZizhuPrint();
	}
	
	public boolean isCardMode(){
		return this.feeMode != null && this.feeMode.isCardMode();
	}
	
	public String getEndDateStr(){
		if(endDate > 0l){
			return DateUtil.getDateStr(endDate);
		}
		
		return null;
	}
	
}
