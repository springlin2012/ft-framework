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
    EXCEPTION_BIZ_VALIDATOR_ERROR("100103", "业务参校验不通过"),
    EXCEPTION_BIZ_CHECK_ERROR("100104", "业务数据检查异常"),

    EB_OPT_DATA("100130", "数据操作异常"),
    EB_DATA_UPDATE_FAIL("100131", "数据更新失败"),
    EB_DATA_QUERY_FAIL("100132", "数据查询失败"),
    EB_DATA_INSERT_FAIL("100133", "数据保存失败"),

    /**
     * 代码出现异常
     */
    EXCEPTION_UNKWOWN("100200", "服务繁忙，请稍后再试"),

    EXCEPTION_BS("100300", "系统基础服务异常"),

    /**
     * 调用远程接口出现异常
     */
    EXCEPTION_RPC("100400", "远程服务调用异常"),
    EXCEPTION_RPC_RSP_ERROR("100401", "远程服务调用返回的业务数据错误"),
    EXCEPTION_RPC_RISK("100401", "风控远程调用错误"),
    EXCEPTION_RPC_MQ("100402", "MQ收发消息异常"),

    /**
     * 相关组件异常
     */
    EXCEPTION_CACHE("100501", "缓存服务异常"),

    /**
     * 业务异常小类
     */
    EXCEPTION_INVALIDE_REQ("100700", "无效的请求"),

    EXCEPTION_SECURITY("100900", "安全错误"),
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

    public static ResultCodeEnum judgeValue(String code) {
        ResultCodeEnum[] codes = ResultCodeEnum.values();
        for (ResultCodeEnum thisCode : codes) {
            if (thisCode.getKey().equals(code)) {
                return thisCode;
            }
        }
        return null;
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
