package com.yisi.stiku.wallet.rpc.constant;

/**
 * 交易类型
 * @author shangfeng
 *
 */
public enum TradeType {

	CHARGE((byte)1, "充值"), 
	AGENT_CHARGE((byte)6, "代理商代充"),
	AGENT_YF((byte)7, "代理商应付款"), //即代理商应付给公司的钱
	AGENT_FK((byte)8, "代理商已付款"), //财务收到代理商的应付款之后，财务确认已收款时的交易类型
	CONSUME((byte)2, "消费"), 
	INCOME((byte)3, "收入"), 
	SYS_PROVIDE((byte)4, "系统发放"), 
	CASH((byte)5, "提现");
	
	private byte dbCode;
	private String desc;
	private TradeType(byte dbCode, String desc){
		this.dbCode = dbCode;
		this.desc = desc;
	}
	
	public byte getDbCode() {
		return dbCode;
	}
	public String getDesc() {
		return desc;
	}
	
	public static TradeType getByDbCode(byte dbCode){
		for(TradeType state : TradeType.values()){
			if(state.getDbCode() == dbCode){
				return state;
			}
		}
		
		throw new IllegalArgumentException("NOT SUPPORTED PARAM");
	}
}
