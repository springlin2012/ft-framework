/*
 * Copyright (c) 2019 KLTB, Inc. All rights reserved.
 *
 * @author lichunlin
 */
package com.kltb.framework.security.common.entity;

import com.kltb.framework.security.common.enums.EncryptTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @descript: 安全加解密基础属性类
 * @date: 2019/11/07.
 */
public class BaseReq implements Serializable {
    private static final long serialVersionUID = 2508412489595902775L;
    public static final String DEFALT_ENCODING_UTF8 = "UTF-8";

    /**
     * 加密算法
     */
    private EncryptTypeEnum encryptType;

    /**
     * 密钥
     */
    private String encryptKey;

    /**
     * 字符编码
     */
    private String encoding;

    /**
     * 签名
     */
    private String sign;

    /**
     * 加密业务数据
     */
    private String bizContent;

    /**
     * 请求时间
     */
    private long timestamp;

    public BaseReq() {
        this.timestamp = System.currentTimeMillis();
    }

    public EncryptTypeEnum getEncryptType() {
        if (null == this.encryptType) {
            this.encryptType = EncryptTypeEnum.AES;
        }
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
        if (StringUtils.isEmpty(encoding)) {
            this.encoding = DEFALT_ENCODING_UTF8;
        }
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
