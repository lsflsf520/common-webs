package com.yisi.stiku.wallet.rpc.constant;

import java.util.Date;

import com.yisi.stiku.common.utils.DateUtil;

public enum JieSuanState {

	CHECKING((byte)0, "审核中"),
	SUCCESS((byte)1, "已结算"),
	NOT_PASS((byte)2, "审核不通过"),
	INVALID((byte)-1, "已废弃")
	;
	
	public final static int SPLIT_RATIO = 50; // 分成比例 0 到 100 之间的数字
	public final static Date JIESUAN_START_DATETIME = DateUtil.parseDateTime("2015-07-20 00:00:00");
	
	private byte dbCode;
	private String desc;
	private JieSuanState(byte dbCode, String desc){
		this.dbCode = dbCode;
		this.desc = desc;
	}
	
	public byte getDbCode() {
		return dbCode;
	}
	public String getDesc() {
		return desc;
	}
	
	public static JieSuanState getByDbCode(byte dbCode){
		for(JieSuanState state : JieSuanState.values()){
			if(state.getDbCode() == dbCode){
				return state;
			}
		}
		
		throw new IllegalArgumentException("NOT SUPPORTED PARAM");
	}
	
}
