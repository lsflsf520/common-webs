package com.yisi.stiku.wallet.rpc.constant;

public enum ResType {
	
	COURSE((byte)1, "课程资源"), 
	PRINT((byte)2, "打印资源"),
	PACKAGE((byte)3, "套餐资源"),
	FUNC((byte)4, "功能资源"),
	;

	private byte dbCode;
	private String desc;
	private ResType(byte dbCode, String desc){
		this.dbCode = dbCode;
		this.desc = desc;
	}
	
	public byte getDbCode() {
		return dbCode;
	}
	public String getDesc() {
		return desc;
	}
	
	public static ResType getByDbCode(byte dbCode){
		for(ResType state : ResType.values()){
			if(state.getDbCode() == dbCode){
				return state;
			}
		}
		
		throw new IllegalArgumentException("NOT SUPPORTED PARAM");
	}
}
