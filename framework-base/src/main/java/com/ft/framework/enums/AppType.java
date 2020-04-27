package com.ft.framework.enums;

/***
 * app类型 枚举
 * 
 *
 */
public enum AppType {

	IOS("ios","ios"), 
	ANDROID("android","android");

	private String msg;
	private String code;

	private AppType(String code,String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
