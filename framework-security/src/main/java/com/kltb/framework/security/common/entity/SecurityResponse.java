/*
 * Copyright (c) 2019 KLTB, Inc. All rights reserved.
 *
 * @author lichunlin
 */
package com.kltb.framework.security.common.entity;

import com.alibaba.fastjson.JSONObject;
import com.kltb.framework.security.common.enums.IEnum;
import com.kltb.framework.security.common.enums.ResultCodeEnum;
import com.kltb.framework.security.sdk.exception.SignException;
import com.kltb.framework.security.sdk.util.AsymmetricUtil;
import com.kltb.framework.security.sdk.util.Base64;
import com.kltb.framework.security.sdk.util.StringUtil;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @descript: 安全响应
 * @auth: lichunlin
 * @date: 2019/11/07.
 */
public class SecurityResponse<T> extends BaseReq {
    private static final long serialVersionUID = -8941299750467897831L;
    private String code;
    private String msg;

    /**
     * 商户号
     */
    private String merchantNo;

    public SecurityResponse() {
        this.code = ResultCodeEnum.SUCCESS.getKey();
        this.msg = ResultCodeEnum.SUCCESS.getValue();
    }

    public SecurityResponse(IEnum resultCode) {
        this.code = resultCode.getKey();
        this.msg = resultCode.getValue();
    }

    public SecurityResponse(T data) {
        this.setBizObject(data);
    }

    public SecurityResponse(IEnum resultCode, String msg) {
        this.code = resultCode.getKey();
        this.msg = msg;
    }

    public SecurityResponse(String errorCode, String msg) {
        this.code = errorCode;
        this.msg = msg;
    }

    public SecurityResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.setBizObject(data);
    }

    /**
     * 签名
     *
     * @param privateKey
     * @throws SignException
     */
    public void sign(PrivateKey privateKey) throws SignException {
        this.doSign(privateKey, null, true);
    }

    public void sign(PrivateKey privateKey, String signAlgorithm) throws SignException {
        this.doSign(privateKey, signAlgorithm, false);
    }

    public void doSign(PrivateKey privateKey, String signAlgorithm, boolean isUsingAppConfig) throws SignException {
        try {
            this.setSign(Base64.byteArrayToBase64(AsymmetricUtil
                .genSignature(this.toSignContent().getBytes(this.getEncoding()), privateKey, signAlgorithm)));
        } catch (Exception e) {
            throw new SignException("签名失败", e);
        }
    }

    public String toSignContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("bizContent").append("=").append(StringUtil.nullToEmpty(this.getBizContent()))
                .append("&").append("encryptKey").append("=").append(StringUtil.nullToEmpty(this.getEncryptKey()))
                .append("&").append("encryptType").append("=").append(StringUtil.nullToEmpty(this.getEncryptType()))
                .append("&").append("code").append("=").append(StringUtil.nullToEmpty(this.getCode()))
                .append("&").append("msg").append("=").append(StringUtil.nullToEmpty(this.getMsg()))
                .append("&").append("timestamp").append("=").append(StringUtil.nullToEmpty(this.getTimestamp()));
        return sb.toString();
    }

    /**
     * 验签
     *
     * @param publicKey
     * @throws SignException
     */
    public void verify(PublicKey publicKey) throws SignException {
        if (!this.doVerify(publicKey, null, true)) {
            throw new SignException("返回验签失败");
        }
    }

    public boolean verify(PublicKey publicKey, String signAlgorithm) throws SignException {
        return this.doVerify(publicKey, signAlgorithm, false);
    }

    public boolean doVerify(PublicKey publicKey, String signAlgorithm, boolean isUsingAppConfig) throws SignException {
        try {
            return AsymmetricUtil.verifySignature(this.toSignContent().getBytes(this.getEncoding()),
                Base64.base64ToByteArray(this.getSign()), publicKey, signAlgorithm);
        } catch (Exception e) {
            throw new SignException("签名验证失败", e);
        }
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

    public void setBizObject(T bizObject) {
        this.setBizContent(JSONObject.toJSONString(bizObject));
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }
}
