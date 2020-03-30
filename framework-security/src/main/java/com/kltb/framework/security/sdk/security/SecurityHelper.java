/*
 * Copyright (c) 2019 KLTB, Inc. All rights reserved.
 *
 * @author lichunlin
 */
package com.kltb.framework.security.sdk.security;

import com.kltb.framework.security.common.entity.SKeyInfo;
import com.kltb.framework.security.common.entity.ServiceRequest;
import com.kltb.framework.security.common.entity.ServiceResponse;
import com.kltb.framework.security.common.enums.EncryptTypeEnum;
import com.kltb.framework.security.sdk.exception.EncodeDecodeException;
import com.kltb.framework.security.sdk.util.AESUtil;
import com.kltb.framework.security.sdk.util.AsymmetricUtil;
import com.kltb.framework.security.sdk.util.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

/**
 * @descript: 安全协议接收辅助类
 * @date: 2019/11/07.
 */
public class SecurityHelper {
    protected static PublicKey publicKey = null;
    protected static PrivateKey privateKey = null;

    public SecurityHelper(SKeyInfo sKeyInfo) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        this.init(sKeyInfo);
    }

    private void init(SKeyInfo sKeyInfo)
            throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        publicKey = AsymmetricKeyHelper.getInstance().readRSAPublicKey(sKeyInfo.getPublicKeyName(),
                sKeyInfo.getPublicKeyPath());
        privateKey = AsymmetricKeyHelper.getInstance().readRSAPrivateKey(sKeyInfo.getPrivateKeyName(),
                sKeyInfo.getPrivateKeyPath());
    }

    /**
     * 解码
     *
     * @param request
     * @throws EncodeDecodeException
     */
    public void decode(ServiceRequest request) throws EncodeDecodeException {
        try {
            request.verify(publicKey);
            if (StringUtils.isNotEmpty(request.getBizContent())) {
                if (EncryptTypeEnum.AES.equals(request.getEncryptType())) {
                    byte[] key =
                        AsymmetricUtil.decryptData(Base64.base64ToByteArray(request.getEncryptKey()), privateKey, null);
                    byte[] byteBizData = AESUtil.decrypt(key, Base64.base64ToByteArray(request.getBizContent()));
                    request.setBizContent(new String(byteBizData, request.getEncoding()));
                }
            }
        } catch (Exception e) {
            throw new EncodeDecodeException("请求编码处理失败", e);
        }
    }

    /**
     * 编码
     *
     * @param response
     * @param response
     * @throws EncodeDecodeException
     */
    public void encode(ServiceResponse response) throws EncodeDecodeException {
        try {
            if (StringUtils.isNotEmpty(response.getBizContent())) {
                if (EncryptTypeEnum.AES.equals(response.getEncryptType())) {
                    byte[] key = AESUtil.generateKey(128);
                    response.setEncryptKey(Base64.byteArrayToBase64(AsymmetricUtil.encryptData(key, publicKey, null)));
                    byte[] byteBizData = AESUtil.encrypt(key, response.getBizContent().getBytes(response.getEncoding()));
                    response.setBizContent(Base64.byteArrayToBase64(byteBizData));
                }
            }
            response.sign(privateKey);
        } catch (Exception e) {
            throw new EncodeDecodeException("编码处理失败", e);
        }
    }
}