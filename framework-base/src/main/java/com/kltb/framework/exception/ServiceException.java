package com.kltb.framework.exception;

import com.kltb.framework.enums.IEnum;
import com.kltb.framework.enums.ResultCodeEnum;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 7344437539128484357L;

    public static final String CODE_DEFAULT = ResultCodeEnum.EXCEPTION_BS.getKey();
    public static final String MSG_DEFAULT = ResultCodeEnum.EXCEPTION_BS.getValue();

    private String code;
    private String msg;

    public ServiceException(Throwable cause) {
        super(ResultCodeEnum.EXCEPTION_UNKWOWN.getValue(), cause);
        this.code = ResultCodeEnum.EXCEPTION_UNKWOWN.getKey();
        this.msg = ResultCodeEnum.EXCEPTION_UNKWOWN.getValue();
    }

    public ServiceException(IEnum ResultCodeEnum) {
        this(ResultCodeEnum.getKey(), ResultCodeEnum.getValue());
    }

    public ServiceException(IEnum ResultCodeEnum, String msg) {
        this(ResultCodeEnum.getKey(), msg);
    }

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String serviceMsg) {
        super(serviceMsg);
        this.code = ResultCodeEnum.EXCEPTION_BUSINESS.getKey();
        this.msg = serviceMsg;
    }

    public ServiceException(String code, String msg, String causeMsg) {
        super(causeMsg);
        this.code = code;
        this.msg = msg;
    }


    public ServiceException() {
        this(CODE_DEFAULT, MSG_DEFAULT);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return String.format("code=%s|msg=%s|error=%s", code, msg, super.toString());
    }

}
