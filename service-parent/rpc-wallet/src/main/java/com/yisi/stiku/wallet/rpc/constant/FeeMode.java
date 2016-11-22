package com.yisi.stiku.wallet.rpc.constant;

public enum FeeMode {

	M12_1800(FeeMode.M1800, "sindex_1", "1800模式"),
	M3_1800(FeeMode.M1800, "sindex_3", "1800模式"),
	
	FREE12(FeeMode.FUNC_CARD, "index12", "高一高二免费模式(高一高二使用)"),
	FREE3(FeeMode.FUNC_CARD, "index3", "高三免费模式(高三使用)"),
	S12(FeeMode.FUNC_CARD, "S12", "激活卡模式，380元激活卡(高一高二使用)"),
	S3(FeeMode.FUNC_CARD, "S3", "激活卡模式，580元激活卡(高三使用)"),
	G12(FeeMode.FUNC_CARD, "G12", "激活卡模式，金牌激活卡(高一高二使用)"),
	G3(FeeMode.FUNC_CARD, "G3", "激活卡模式，金牌激活卡(高三使用)"),
	D12(FeeMode.FUNC_CARD, "D12", "激活卡模式，钻石激活卡(高一高二使用)"),
	D3(FeeMode.FUNC_CARD, "D3", "激活卡模式，钻石激活卡(高三使用)"),
	
	ZIZHU_PRINT12(FeeMode.ZIZHU_PRINT, "sindex_print_12", "自助打印模式(高一高二使用)"),
	ZIZHU_PRINT3(FeeMode.ZIZHU_PRINT, "sindex_print_3", "自助打印模式(高三使用)")
	;
	
	private final static String M1800 = "M1800";
	private final static String FUNC_CARD = "FUNC_CARD";
	private final static String ZIZHU_PRINT = "ZIZHU_PRINT";
	
	private final String modeCode; //模式编码
	private final String indexAddr;
	private final String desc;
	
	private FeeMode(String modeCode, String indexAddr, String desc){
		this.modeCode = modeCode;
		this.indexAddr = indexAddr;
		this.desc = desc;
	}
	
	public String getIndexAddr() {
		return indexAddr;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public boolean is1800Mode(){
		return M1800.equals(modeCode);
	}
	
	public boolean isZizhuPrint(){
		return ZIZHU_PRINT.equals(modeCode);
	}
	
	public boolean isCardMode(){
		return FUNC_CARD.equals(modeCode);
	}

}