package com.yisi.stiku.intg.constant;

/**
 * @author shangfeng
 *
 */
public enum DmStatus {

	INVALID((byte) -1, "已废弃"),
	REVIEW((byte) 0, "评审中"),
	DEV((byte) 5, "开发中"),
	TEST((byte) 10, "测试中"),
	ONLINE((byte) 15, "已上线");

	private byte dbCode;
	private String desc;

	private DmStatus(byte dbCode, String desc) {

		this.dbCode = dbCode;
		this.desc = desc;
	}

	public Byte getDbCode() {

		return dbCode;
	}

	public String getDesc() {

		return desc;
	}

	// @Override
	// public EntityState[] getValues() {
	// return EntityState.values();
	// }

	public static DmStatus getByDbCode(byte dbCode) {

		for (DmStatus state : DmStatus.values()) {
			if (state.getDbCode() == dbCode) {
				return state;
			}
		}

		throw new IllegalArgumentException("NOT SUPPORTED PARAM");
	}

}
