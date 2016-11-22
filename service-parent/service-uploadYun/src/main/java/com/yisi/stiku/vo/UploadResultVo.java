package com.yisi.stiku.vo;

public class UploadResultVo {
	protected String outterId;
	protected boolean result;
	
	public String getOutterId() {
		return outterId;
	}
	public void setOutterId(String outterId) {
		this.outterId = outterId;
	}
	public boolean getResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
	public UploadResultVo(String outterId, boolean result) {
		super();
		this.outterId = outterId;
		this.result = result;
	}
	
}
