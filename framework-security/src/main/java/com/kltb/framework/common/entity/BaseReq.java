/*
 * Copyright (c) 2019 KLTB, Inc. All rights reserved.
 *
 * @author lichunlin
 */
package com.kltb.framework.common.entity;

import com.kltb.framework.common.enums.EncryptTypeEnum;

import java.io.Serializable;

/**
 * @descript: 安全加解密基础属性类
 * @date: 2019/11/07.
 */
public class BaseReq implements Serializable {
    private static final long serialVersionUID = 1809313804139359530L;
    protected final String DEFALT_ENCODING_UTF8 = "UTF-8";

    /**
     * 加密算法
     */
    private EncryptTypeEnum encryptType;

    /**
     * 密钥
     */
    private String encryptKey;

    /**
     * 加密业务数据
     */
    private String bizContent;

    /**
     * 签名
     */
    private String sign;

    /**
     * 字符编码
     */
    private String encoding;

    public EncryptTypeEnum getEncryptType() {
        return encryptType;
    }

    public void setEncryptType(EncryptTypeEnum encryptType) {
        this.encryptType = encryptType;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    public String getBizContent() {
        return bizContent;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getEncoding() {
        if (null == encoding || encoding.trim().equals("")) {
            encoding = DEFALT_ENCODING_UTF8;
        }
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
