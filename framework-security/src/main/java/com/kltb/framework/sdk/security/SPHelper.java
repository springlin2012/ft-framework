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
 * @descript: 安全协议接收辅助类
 * @date: 2019/11/07.
 */
public class SPHelper extends SecurityProtocol {

    public SPHelper(SKeyInfo sKeyInfo) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        super(sKeyInfo);
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
            if (!StringUtil.isEmpty(request.getBizContent())) {
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
            if (StringUtil.isEmpty(response.getBizContent())) {
                response.sign(privateKey);
            } else {
                if (EncryptTypeEnum.AES.equals(response.getEncryptType())) {
                    byte[] key = AESUtil.generateKey(128);
                    response.setEncryptKey(Base64.byteArrayToBase64(AsymmetricUtil.encryptData(key, publicKey, null)));
                    byte[] byteBizData = AESUtil.encrypt(key, response.getBizContent().getBytes(DEFUALT_ENDCODING));
                    response.setBizContent(Base64.byteArrayToBase64(byteBizData));
                }
                response.sign(privateKey);
            }
        } catch (Exception e) {
            throw new EncodeDecodeException("编码处理失败", e);
        }
    }
}
