package com.kltb.framework.enums;

/**
 * @Description: 终端类型
 */
public enum TerminalType {
	
	APP("app","app"), 
	WEB("web","web"),
	H5("h5","h5");
	  
	private TerminalType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private String code;
	private String desc; 
	
	public String getCode() {	
		return this.code;
	}

	public String getDesc() {	
		return this.desc;
	}
	
	/**
	 * 判断码终端类型是否正确
	 * @param code 终端类型码
	 * @return
	 * @author weiqc
	 * @since 2014年8月11日
	 */
	public static TerminalType judgeValue(String code){
		
		TerminalType[] terminalTypes = TerminalType.values();
		for(TerminalType terminalType : terminalTypes){
			if(terminalType.getCode().equals(code)){
				return terminalType;
			}
		}
		return null;
	}
	
}
