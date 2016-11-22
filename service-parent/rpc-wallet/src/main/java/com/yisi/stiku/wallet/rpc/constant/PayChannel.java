package com.yisi.stiku.wallet.rpc.constant;


public enum PayChannel {
	
	DEFAULT((byte)0, "默认渠道"),
	ZFB_ONLINE((byte)11, "支付宝线上支付渠道"), 
	YL_ONLINE((byte)12, "银联线上支付渠道"),
	AGENT_OFFLINE((byte)51, "代理商线下收取"),
	FUNC_CARD((byte)52, "功能卡")
	;

	private byte dbCode;
	private String desc;
	private PayChannel(byte dbCode, String desc){
		this.dbCode = dbCode;
		this.desc = desc;
	}
	
	public byte getDbCode() {
		return dbCode;
	}
	public String getDesc() {
		return desc;
	}
	
	public static PayChannel getByDbCode(byte dbCode){
		for(PayChannel state : PayChannel.values()){
			if(state.getDbCode() == dbCode){
				return state;
			}
		}
		
		throw new IllegalArgumentException("NOT SUPPORTED PARAM");
	}
	
}
