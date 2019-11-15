package com.kltb.framework.exception;

public final class ParamException extends RuntimeException{

	private static final long serialVersionUID = 3456601877949306994L;

	private String paramName;
	
	private String errorMsg;
	
	public ParamException(String paramName, String errorMsg) {
		super(errorMsg);
		this.paramName = paramName;
		this.errorMsg = errorMsg;
	}
	
	public ParamException(String errorMsg) {
		super(errorMsg);
		this.errorMsg = errorMsg;
	}
	
	public ParamException(String paramName, String errorMsg, Throwable cause) {
		super(errorMsg,cause);
		this.paramName = paramName;
		this.errorMsg = errorMsg;
	}
	
	public ParamException(String errorMsg, Throwable cause) {
		super(errorMsg,cause);
		this.errorMsg = errorMsg;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return String.format("paramName=%s|errorMsg=%s|error=%s",paramName,errorMsg,super.toString());
	}
	
}
