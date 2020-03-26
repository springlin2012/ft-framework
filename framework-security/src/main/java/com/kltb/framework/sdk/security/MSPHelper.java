/*
 * Copyright (c) 2019 KLTB, Inc. All rights reserved.
 *
 * @author lichunlin
 */
package com.kltb.framework.sdk.security;

import com.kltb.framework.common.entity.SKeyInfo;
import com.kltb.framework.common.entity.ServiceRequest;
import com.kltb.framework.common.entity.ServiceResponse;
import com.kltb.framework.common.enums.EncryptTypeEnum;
import com.kltb.framework.sdk.exception.EncodeDecodeException;
import com.kltb.framework.sdk.util.AESUtil;
import com.kltb.framework.sdk.util.AsymmetricUtil;
import com.kltb.framework.sdk.util.Base64;
import com.kltb.framework.sdk.util.StringUtil;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @descript: 安全协议请求辅助类
 * @auth: lichunlin
 * @date: 2019/11/07.
 */
public class MSPHelper extends SecurityProtocol {

    public MSPHelper(SKeyInfo sKeyInfo)
        throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        super(sKeyInfo);
    }

    /**
     * 编码
     *
     * @param request
     * @throws EncodeDecodeException
     */
    public void encode(ServiceRequest request) throws EncodeDecodeException {
        try {
            if (StringUtil.isEmpty(request.getBizContent())) {
                request.sign(privateKey);
            } else {
                if (EncryptTypeEnum.AES.equals(request.getEncryptType())) {
                    byte[] key = AESUtil.generateKey(128);
                    request.setEncryptKey(Base64.byteArrayToBase64(AsymmetricUtil.encryptData(key, publicKey, null)));
                    byte[] byteBizData = AESUtil.encrypt(key, request.getBizContent().getBytes(request.getEncoding()));
                    request.setBizContent(Base64.byteArrayToBase64(byteBizData));
                }
                request.sign(privateKey);
            }
        } catch (Exception e) {
            throw new EncodeDecodeException("请求编码处理失败", e);
        }
    }

    /**
     * 解码
     *
     * @param response
     * @param response
     * @throws EncodeDecodeException
     */
    public void decode(ServiceResponse response) throws EncodeDecodeException {
        try {
            response.verify(publicKey);
            if (!StringUtil.isEmpty(response.getBizContent())) {
                if (EncryptTypeEnum.AES.equals(response.getEncryptType())) {
                    byte[] key = AsymmetricUtil.decryptData(Base64.base64ToByteArray(response.getEncryptKey()),
                        privateKey, null);
                    byte[] byteBizData = AESUtil.decrypt(key, Base64.base64ToByteArray(response.getBizContent()));
                    response.setBizContent(new String(byteBizData, response.getEncoding()));
                }

            }
        } catch (Exception e) {
            throw new EncodeDecodeException("返回解码处理处理失败", e);
        }
    }
}
