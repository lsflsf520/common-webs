package com.yisi.stiku.wallet.rpc.constant;

public enum FuncCardState {

	SALE_ING((byte)0, "未激活"), //这里应该是待销售
	ACT_ING((byte)1, "未激活"),
	USE_ING((byte)2, "已激活"),
	EXPIRED((byte)-2, "已过期"),
	INVALID((byte)-1, "已作废")
	;

	private byte dbCode;
	private String desc;
	private FuncCardState(byte dbCode, String desc){
		this.dbCode = dbCode;
		this.desc = desc;
	}
	
	public byte getDbCode() {
		return dbCode;
	}
	public String getDesc() {
		return desc;
	}
	
	public static FuncCardState getByDbCode(byte dbCode){
		for(FuncCardState state : FuncCardState.values()){
			if(state.getDbCode() == dbCode){
				return state;
			}
		}
		
		throw new IllegalArgumentException("NOT SUPPORTED PARAM");
	}
	
}
