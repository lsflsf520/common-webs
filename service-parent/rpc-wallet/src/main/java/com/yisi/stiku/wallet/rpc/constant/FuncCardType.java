package com.yisi.stiku.wallet.rpc.constant;

/**
 * 
 * @author shangfeng
 *
 */
public enum FuncCardType {
	S12(10, "S", "银牌速学客", "/images/silver.gif"), 
	S3(11, "S", "银牌速学客", "/images/silver.gif"),
	G12(50, "G", "金牌魔数师", "/images/gold.gif"),
	G3(51, "G", "金牌魔数师", "/images/gold.gif"),
	D12(80, "D", "钻石艺数家", "/images/silver-medal1.png"),
	D3(81, "D", "钻石艺数家", "/images/silver-medal1.png")
	;

	private int level;
	private String dbCode;
	private String desc;
	private String animationImg;//动画图片
	private FuncCardType(int level, String dbCode, String desc, String animationImg){
		this.dbCode = dbCode;
		this.desc = desc;
		this.level = level;
		this.animationImg = animationImg;
	}
	
	public String getDbCode() {
		return dbCode;
	}
	public String getDesc() {
		return desc;
	}
	
	public int getLevel() {
		return level;
	}
	
	public String getAnimationImg() {
		return animationImg;
	}

	public static FuncCardType getByDbCode(String dbCode){
		for(FuncCardType state : FuncCardType.values()){
			if(state.getDbCode() == dbCode){
				return state;
			}
		}
		
		throw new IllegalArgumentException("NOT SUPPORTED PARAM");
	}
}
