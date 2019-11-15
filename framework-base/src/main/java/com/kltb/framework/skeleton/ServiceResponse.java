package com.kltb.framework.skeleton;

import com.alibaba.fastjson.annotation.JSONField;
import com.kltb.framework.base.BaseEntity;
import com.kltb.framework.enums.IEnum;
import com.kltb.framework.enums.ResultCodeEnum;

import java.io.Serializable;

public class ServiceResponse<T> extends BaseEntity {
    private static final long serialVersionUID = 3740912671885315905L;

    private String code = ResultCodeEnum.SUCCESS.getKey();
    private String msg = ResultCodeEnum.SUCCESS.getValue();
    private T data;

    public ServiceResponse() {}


    public ServiceResponse(IEnum errorCode, String msg) {
        this.code = errorCode.getKey();
        this.msg = msg;
    }

    public void ResponseInfoEntity(IEnum errorCode) {
        this.code = errorCode.getKey();
        this.msg = errorCode.getValue();
    }

	public ServiceResponse(T data) {
		this.data = data;
	}

	public ServiceResponse(String code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

    /**
     * 构造函数(失败)
     * 
     * @param errorCode
     */
    public ServiceResponse(IEnum errorCode) {
        this(null, errorCode);
    }

	public ServiceResponse(String errorCode, String msg) {
		this.code = errorCode;
		this.msg = msg;
	}

    /**
     * 构造函数
     * 
     * @param data 数据
     * @param head 消息头
     */
    public ServiceResponse(T data, IEnum head) {
        this.code = head.getKey();
        this.msg = head.getValue();
        this.data = data;
    }

    /**
     * 是否成功
     *
     * @return true成功，false失败
     */
    @JSONField(serialize = false)
    public boolean isSuccess() {
        return ResultCodeEnum.SUCCESS.getKey().equals(this.code);
    }

    /**
     * 是否错误
     *
     * @return true 失败，false成功
     */
    @JSONField(serialize = false)
    public boolean isError() {
        return !isSuccess();
    }

    /**
     * 是什么类型错误
	 *
     * @param error 具体错误
     * @return
     */
    @JSONField(serialize = false)
    public boolean isError(ResultCodeEnum error) {
        if (error == null) {
            return isError();
        }
        return this.code == error.getKey();
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
