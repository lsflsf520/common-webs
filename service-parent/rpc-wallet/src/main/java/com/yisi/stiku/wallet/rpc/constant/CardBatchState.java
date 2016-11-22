package com.yisi.stiku.wallet.rpc.constant;

public enum CardBatchState {

	GEN_ING((byte)0, "生成中"), 
	GEN_END((byte)1, "已生成完毕"),
	INVALID((byte)-1, "已作废")
	;

	private byte dbCode;
	private String desc;
	private CardBatchState(byte dbCode, String desc){
		this.dbCode = dbCode;
		this.desc = desc;
	}
	
	public byte getDbCode() {
		return dbCode;
	}
	public String getDesc() {
		return desc;
	}
	
	public static CardBatchState getByDbCode(byte dbCode){
		for(CardBatchState state : CardBatchState.values()){
			if(state.getDbCode() == dbCode){
				return state;
			}
		}
		
		throw new IllegalArgumentException("NOT SUPPORTED PARAM");
	}
	
}
