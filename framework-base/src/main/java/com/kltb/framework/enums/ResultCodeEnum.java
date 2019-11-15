package com.kltb.framework.enums;


import org.apache.commons.lang3.StringUtils;

/**
 * @descript: 异常编码定义
 * @author : lcl
 * @date: 2019/09/05.
 */
public enum ResultCodeEnum implements IBaseEnum<ResultCodeEnum> {

    SUCCESS("200", "成功"),

    /**
     * 全局业务异常大类
     */
    EXCEPTION_BUSINESS("100100", "业务异常"),

    EXCEPTION_BUSINESS_PARAM_MISS("100101", "参数缺失"),

    EXCEPTION_BUSINESS_PARAM_ERROR("100102", "参数错误"),

    /**
     * 代码出现异常
     */
    EXCEPTION_UNKWOWN("100200", "服务繁忙，请稍后再试"),

    EXCEPTION_BS("100300", "系统基础服务异常"),

    /**
     * 调用远程接口出现异常
     */
    EXCEPTION_RPC("100400", "远程服务调用异常"),

    /**
     * 调用远程接口正常返回数据，但是返回数据出现错误
     */
    EXCEPTION_RPC_RSP_ERROR("100401", "远程服务调用返回的业务数据错误"),

    EXCEPTION_CACHE("100500", "缓存服务异常"),

    /**
     * 这是个业务异常的一个小类
     */
    EXCEPTION_INVALIDE_REQ("100700", "无效的请求"),

    EXCEPTION_SECURITY("00900", "安全错误"),

    ;

    private String key;
    private String value;

    private ResultCodeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public ResultCodeEnum setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public boolean isSuccess(String key) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        return this.key.equals(key);
    }

}
