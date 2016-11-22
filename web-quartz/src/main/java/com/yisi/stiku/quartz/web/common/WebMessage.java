package com.yisi.stiku.quartz.web.common;

public class WebMessage {

	private String message;

	private String[] codes;

	public WebMessage(String[] code2, String defaultMessage) {

		this.codes = code2;
		this.message = defaultMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String[] getCodes() {
		return codes;
	}

	public void setCodes(String[] codes) {
		this.codes = codes;
	}

}
