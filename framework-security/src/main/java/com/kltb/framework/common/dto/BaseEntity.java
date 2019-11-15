/*
 * Copyright (c) 2019 KLTB, Inc. All rights reserved.
 *
 * @author lichunlin
 */
package com.kltb.framework.common.dto;

import com.kltb.framework.common.enums.EncryptTypeEnum;

import java.io.Serializable;

/**
 * @descript: 安全加解密基础属性类
 * @auth: lichunlin
 * @date: 2019/11/07.
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1809313804139359530L;

    /**
     * 加密算法
     */
    private EncryptTypeEnum encryptType;

    /**
     * 密钥
     */
    private String encryptKey;

    /**
     * 业务数据
     */
    private String bizData;

    /**
     * 签名
     */
    private String sign;

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

    public String getBizData() {
        return bizData;
    }

    public void setBizData(String bizData) {
        this.bizData = bizData;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
