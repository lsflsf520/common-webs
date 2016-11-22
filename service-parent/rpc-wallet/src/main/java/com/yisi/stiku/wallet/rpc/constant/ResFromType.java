package com.yisi.stiku.wallet.rpc.constant;

public enum ResFromType {
	
	DEFAULT((byte)0, "不需要显示给代理商的功能"),
	SYSTEM((byte)1, "系统生成"), 
	TEACHER((byte)2, "老师创建"),
	AGENT((byte)3, "代理商创建")
	;

	private byte dbCode;
	private String desc;
	private ResFromType(byte dbCode, String desc){
		this.dbCode = dbCode;
		this.desc = desc;
	}
	
	public byte getDbCode() {
		return dbCode;
	}
	public String getDesc() {
		return desc;
	}
	
	public static ResFromType getByDbCode(byte dbCode){
		for(ResFromType state : ResFromType.values()){
			if(state.getDbCode() == dbCode){
				return state;
			}
		}
		
		throw new IllegalArgumentException("NOT SUPPORTED PARAM");
	}
}
