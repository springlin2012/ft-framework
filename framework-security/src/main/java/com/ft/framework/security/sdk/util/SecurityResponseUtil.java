package com.ft.framework.security.sdk.util;


import com.ft.framework.security.common.enums.IEnum;
import com.ft.framework.security.common.enums.ResultCodeEnum;
import com.ft.framework.security.common.entity.SecurityResponse;

/**
 * @descript: 安全响应返回
 * @auth: lichunlin
 * @date: 2019/09/09.
 */
public class SecurityResponseUtil {

    /**
     * 结果转服务输出
     */
    public static <T> SecurityResponse<T> createResponse() {
        return new SecurityResponse<T>();
    }

    public static <T> SecurityResponse<T> createResponse(T rlt) {
        return new SecurityResponse<T>(rlt);
    }

    public static <T> SecurityResponse<T> createResponse(String merchantNo, T rlt) {
        return new SecurityResponse<T>(merchantNo, rlt);
    }

    /**
     * 异常转服务输出
     *
     * @param resultCode
     *            异常错误码
     * @param e
     *            异常
     */
    public static <T> SecurityResponse<T> createResponse(IEnum resultCode, Exception e) {
        return new SecurityResponse<T>(resultCode);
    }

    /**
     * 异常转服务输出
     *
     * @param resultCode
     *            异常错误码
     *
     */
    public static <T> SecurityResponse<T> createResponse(IEnum resultCode) {
        return new SecurityResponse<T>(resultCode);
    }

    /**
     * 异常转服务输出
     *
     * @param resultCode
     *            异常错误码
     * @param msg
     *            异常业务信息
     */
    public static <T> SecurityResponse<T> createResponse(IEnum resultCode, String msg) {
        return new SecurityResponse<T>(resultCode, msg);
    }

    /**
     * 异常转服务输出
     *
     * @param msg
     *            异常业务信息
     *
     */
    public static <T> SecurityResponse<T> createResponseForBisError(String msg) {
        return createResponse(ResultCodeEnum.EXCEPTION_BUSINESS, msg);
    }

    /**
     * 参数异常封装
     *
     * @param msg
     *            异常业务信息
     *
     */
    public static <T> SecurityResponse<T> createResponseForParamError(String msg) {
        return createResponse(ResultCodeEnum.EXCEPTION_BUSINESS_PARAM_ERROR, msg);
    }

    /**
     * 输出自定义业务异常
     *
     * @param resultCode
     *            异常业务信息
     *
     */
    public static <T> SecurityResponse<T> createResponseForBisError(IEnum resultCode) {
        return new SecurityResponse<T>(resultCode.getKey(), resultCode.getValue());
    }

    public static <T> SecurityResponse<T> createResponseForBisError(IEnum resultCode, String msg) {
        return new SecurityResponse<T>(resultCode.getKey(), msg);
    }



    /**
     * 异常转服务输出
     *
     * @param resultCode
     *            异常错误码
     * @param msg
     *            异常业务信息
     * @param e
     *            异常(用于记error日志)
     */
    public static <T> SecurityResponse<T> createResponse(IEnum resultCode, String msg, Exception e) {
        return new SecurityResponse<T>(resultCode, msg);
    }

    /**
     * 异常转服务输出
     *
     * @param msg
     *            异常业务信息
     * @param e
     *            异常(用于记error日志)
     */
    public static <T> SecurityResponse<T> createResponseForBisError(String msg, Exception e) {
        return createResponse(ResultCodeEnum.EXCEPTION_BUSINESS, msg, e);
    }
}
