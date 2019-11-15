/*
 * Copyright (c) 2019 KLTB, Inc. All rights reserved.
 *
 * @author lichunlin
 */
package com.kltb.framework.sdk.security;

import com.kltb.framework.common.dto.CommonServiceRequest;
import com.kltb.framework.common.dto.CommonServiceResponse;
import com.kltb.framework.common.dto.SecurityKeyInfo;
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
 * @auth: lichunlin
 * @date: 2019/11/07.
 */
public class SPAcceptorHelper extends SecurityProtocol {

    public SPAcceptorHelper(SecurityKeyInfo securityKeyInfo)
        throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        super(securityKeyInfo);
    }

    /**
     * 请求数据解码
     *
     * @param request
     * @throws EncodeDecodeException
     */
    public void decode(CommonServiceRequest request) throws EncodeDecodeException {
        String encoding = request.getEncoding() == null ? DEFUALT_ENDCODING : request.getEncoding();
        try {
            request.verify(publicKey);
            if (!StringUtil.isEmpty(request.getBizData())) {
                if (EncryptTypeEnum.AES.equals(request.getEncryptType())) {
                    byte[] key = AsymmetricUtil.decryptData(Base64.base64ToByteArray(request.getEncryptKey()),
                            privateKey, null);
                    byte[] byteBizData = AESUtil.decrypt(key, Base64.base64ToByteArray(request.getBizData()));
                    request.setBizData(new String(byteBizData, encoding));
                }

            }
        } catch (Exception e) {
            throw new EncodeDecodeException("请求编码处理失败", e);
        }
    }


    /**
     * 返回数据编码
     *
     * @param response
     * @param response
     * @throws EncodeDecodeException
     */
    public void encode(CommonServiceResponse response) throws EncodeDecodeException {
        try {
            if (StringUtil.isEmpty(response.getBizData())) {
                response.sign(privateKey);
            } else {
                if (EncryptTypeEnum.AES.equals(response.getEncryptType())) {
                    byte[] key = AESUtil.generateKey(128);
                    response.setEncryptKey(
                            Base64.byteArrayToBase64(AsymmetricUtil.encryptData(key, publicKey, null)));
                    byte[] byteBizData = AESUtil.encrypt(key, response.getBizData().getBytes(DEFUALT_ENDCODING));
                    response.setBizData(Base64.byteArrayToBase64(byteBizData));
                }
                response.sign(privateKey);
            }
        } catch (Exception e) {
            throw new EncodeDecodeException("返回解码处理处理失败", e);
        }
    }
}
