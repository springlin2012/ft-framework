/*
 * Copyright (c) 2019 KLTB, Inc. All rights reserved.
 *
 * @author lichunlin
 */
package com.kltb.framework.common.entity;

import com.alibaba.fastjson.JSONObject;
import com.kltb.framework.sdk.exception.SignException;
import com.kltb.framework.sdk.util.AsymmetricUtil;
import com.kltb.framework.sdk.util.Base64;
import com.kltb.framework.sdk.util.StringUtil;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @descript: 服务请求返回属性类
 * @auth: lichunlin
 * @date: 2019/11/07.
 */
public class ServiceResponse<T> extends BaseReq {
    private static final long serialVersionUID = -8941299750467897831L;
    private String code;
    private String msg;

    private T bizObject;

    private String responseDateTime;

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
            this.setSign(Base64.byteArrayToBase64(
                AsymmetricUtil.genSignature(this.toSignContent().getBytes(DEFALT_ENCODING_UTF8), privateKey, signAlgorithm)));
        } catch (Exception var5) {
            throw new SignException("签名失败", var5);
        }
    }

    public String toSignContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("bizContent").append("=").append(StringUtil.nullToEmpty(this.getBizContent())).append("&")
            .append("encryptKey").append("=").append(StringUtil.nullToEmpty(this.getEncryptKey())).append("&")
            .append("encryptType").append("=").append(StringUtil.nullToEmpty(this.getEncryptType())).append("&")
            .append("code").append("=").append(StringUtil.nullToEmpty(this.getCode())).append("&").append("msg")
            .append("=").append(StringUtil.nullToEmpty(this.getMsg())).append("&").append("responseDateTime")
            .append("=").append(StringUtil.nullToEmpty(this.getResponseDateTime()));
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
        } catch (Exception var5) {
            throw new SignException("签名验证失败", var5);
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

    public T getBizObject() {
        return bizObject;
    }

    public void setBizObject(T bizObject) {
        this.setBizContent(JSONObject.toJSONString(bizObject));
    }


    public String getResponseDateTime() {
        return responseDateTime;
    }

    public void setResponseDateTime(String responseDateTime) {
        this.responseDateTime = responseDateTime;
    }
}
