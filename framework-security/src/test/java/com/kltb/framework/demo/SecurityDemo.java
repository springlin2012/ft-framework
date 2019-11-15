/*
 * Copyright (c) 2019 KLTB, Inc. All rights reserved.
 *
 * @author lichunlin
 */
package com.kltb.framework.demo;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.kltb.framework.common.dto.SecurityKeyInfo;
import com.kltb.framework.common.dto.ServiceRequest;
import com.kltb.framework.common.dto.ServiceResponse;
import com.kltb.framework.common.enums.EncryptTypeEnum;
import com.kltb.framework.sdk.exception.EncodeDecodeException;
import com.kltb.framework.sdk.security.SPAcceptorHelper;
import com.kltb.framework.sdk.security.SPHelper;
import com.kltb.framework.sdk.util.DateUtil;

import java.util.Map;

/**
 * @descript: 加解密Demo
 * @auth: lichunlin
 * @date: 2019/11/07.
 */
public class SecurityDemo {

    public static void main(String[] args) {
        SecurityKeyInfo keyInfo = new SecurityKeyInfo();
        keyInfo.setPrivateKeyName("app_private_key");
        keyInfo.setPrivateKeyPath("classpath:keys/app_private_key.pem");
        keyInfo.setPublicKeyName("platform_public_key");
        keyInfo.setPublicKeyPath("classpath:keys/platform_public_key.pem");

        try {
            // request encode,decode
            SPHelper spHelper = new SPHelper(keyInfo);
            ServiceRequest encodeServiceReq = encodeDemo(spHelper);

            SPAcceptorHelper spAcceptorHelper = new SPAcceptorHelper(keyInfo);
            decodeDemo(spAcceptorHelper, encodeServiceReq);

            // response encode,decode
            ServiceResponse response = responseDecodeDemo(spAcceptorHelper);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static ServiceRequest encodeDemo(SPHelper spHelper) throws EncodeDecodeException {
        ServiceRequest request = new ServiceRequest<Map<String, Object>>();
        request.setMerchantNo("kltb101");
        request.setAppId("10101");
        request.setRequestFlowNo("10101" + System.currentTimeMillis());
        request.setRequestDateTime(DateUtil.now());
        request.setEncoding("UTF-8");
        request.setEncryptType(EncryptTypeEnum.AES);

        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("userName", "张三");
        paramMap.put("certNo", "430426199010208888");
        paramMap.put("mobile", "18676756101");
        request.setBizObject(paramMap);

        spHelper.encode(request);
        System.out.println("encode -> request data: "+ JSONObject.toJSONString(request));

        return request;
    }

    public static void decodeDemo(SPAcceptorHelper spAcceptorHelper, ServiceRequest request) throws EncodeDecodeException {
        spAcceptorHelper.decode(request);
        System.out.println("decode -> request data: "+ JSONObject.toJSONString(request));
    }


    public static ServiceResponse responseDecodeDemo(SPAcceptorHelper spAcceptorHelper) throws EncodeDecodeException {
        ServiceResponse response = new ServiceResponse<Map<String, Object>>();
        response.setResultCode("200");
        response.setResultMsg("成功");
        response.setResponseDateTime(DateUtil.now());

        spAcceptorHelper.encode(response);
        System.out.println("decode -> response data: "+ JSONObject.toJSONString(response));

        return response;
    }
}
