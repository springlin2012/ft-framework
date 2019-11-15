package com.kltb.framework.enums;

public enum YesOrNotEnum implements IEnum {
	YES("1","Y"),
	NOT("0","N");
	
	YesOrNotEnum(String key,String value){
		this.key = key;
		this.value = value;
	}

	private String key;
	private String value;
	
	public String getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
	
}
