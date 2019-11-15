package com.kltb.framework.util;

import com.kltb.framework.enums.IEnum;
import com.kltb.framework.enums.ResultCodeEnum;
import com.kltb.framework.skeleton.ServiceResponse;

/**
 * @descript: 服务接口返回辅助类
 * @auth: lichunlin
 * @date: 2019/09/09.
 */
public class ServiceResponseUtil {

    /**
     * 查询结果转服务输出
     */
    public static <T> ServiceResponse<T> createResponseInfo(T rlt) {
        return new ServiceResponse<T>(rlt);
    }

    /**
     * 查询结果转服务输出
     */
    public static <T> ServiceResponse<T> createResponseInfo() {
        return new ServiceResponse<T>();
    }


    /**
     * 异常转服务输出
     *
     * @param resultCode
     *            异常错误码
     * @param e
     *            异常
     */
    public static <T> ServiceResponse<T> createResponseInfo(IEnum resultCode, Exception e) {
        return new ServiceResponse<T>(resultCode);
    }

    /**
     * 异常转服务输出
     *
     * @param resultCode
     *            异常错误码
     *
     */
    public static <T> ServiceResponse<T> createResponseInfo(IEnum resultCode) {
        return new ServiceResponse<T>(resultCode);
    }

    /**
     * 异常转服务输出
     *
     * @param resultCode
     *            异常错误码
     * @param msg
     *            异常业务信息
     */
    public static <T> ServiceResponse<T> createResponseInfo(IEnum resultCode, String msg) {
        return new ServiceResponse<T>(resultCode, msg);
    }

    /**
     * 异常转服务输出
     *
     * @param msg
     *            异常业务信息
     *
     */
    public static <T> ServiceResponse<T> createResponseInfoForBisError(String msg) {
        return createResponseInfo(ResultCodeEnum.EXCEPTION_BUSINESS, msg);
    }

    /**
     * 参数异常封装
     *
     * @param msg
     *            异常业务信息
     *
     */
    public static <T> ServiceResponse<T> createResponseInfoForParamError(String msg) {
        return createResponseInfo(ResultCodeEnum.EXCEPTION_BUSINESS_PARAM_ERROR, msg);
    }

    /**
     * 输出自定义业务异常
     *
     * @param resultCode
     *            异常业务信息
     *
     */
    public static <T> ServiceResponse<T> createResponseInfoForBisError(IEnum resultCode) {
        return new ServiceResponse<T>(resultCode.getKey(), resultCode.getValue());
    }

    public static <T> ServiceResponse<T> createResponseInfoForBisError(IEnum resultCode, String msg) {
        return new ServiceResponse<T>(resultCode.getKey(), msg);
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
    public static <T> ServiceResponse<T> createResponseInfo(IEnum resultCode, String msg, Exception e) {
        return new ServiceResponse<T>(resultCode, msg);
    }

    /**
     * 异常转服务输出
     *
     * @param msg
     *            异常业务信息
     * @param e
     *            异常(用于记error日志)
     */
    public static <T> ServiceResponse<T> createResponseInfoForBisError(String msg, Exception e) {
        return createResponseInfo(ResultCodeEnum.EXCEPTION_BUSINESS, msg, e);
    }
}
