/*
 * Copyright (c) 2019 KLTB, Inc. All rights reserved.
 *
 * @author lichunlin
 */
package com.kltb.framework.demo;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.kltb.framework.common.entity.SKeyInfo;
import com.kltb.framework.common.entity.ServiceRequest;
import com.kltb.framework.common.entity.ServiceResponse;
import com.kltb.framework.sdk.exception.EncodeDecodeException;
import com.kltb.framework.sdk.security.SPHelper;
import com.kltb.framework.sdk.security.MSPHelper;
import com.kltb.framework.sdk.util.DateUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @descript: 加解密Demo
 * @auth: lichunlin
 * @date: 2019/11/07.
 */
public class SecurityDemo {

    public static void main(String[] args) {
        SKeyInfo mKeyInfo = new SKeyInfo();
        mKeyInfo.setPrivateKeyName("app_private_key");
        mKeyInfo.setPrivateKeyPath("classpath:keys/app_private_key.pem");
        mKeyInfo.setPublicKeyName("platform_public_key");
        mKeyInfo.setPublicKeyPath("classpath:keys/platform_public_key.pem");

        SKeyInfo pKeyInfo = new SKeyInfo();
        pKeyInfo.setPublicKeyName("app_public_key");
        pKeyInfo.setPublicKeyPath("classpath:keys/app_public_key.pem");
        pKeyInfo.setPrivateKeyName("platform_private_key");
        pKeyInfo.setPrivateKeyPath("classpath:keys/platform_private_key.pem");

        try {
            // merchant encode
            MSPHelper mspHelper = new MSPHelper(mKeyInfo);
            // ServiceRequest encodeServiceReq = encodeDemo(mspHelper);
            // platform decode
            SPHelper spHelper = new SPHelper(pKeyInfo);
            // decodeDemo(spHelper, encodeServiceReq);

            // platform encode
            ServiceResponse encodeServiceResponse = responseDecodeDemo(spHelper);
            // merchant decode
            responseDecodeDemo(mspHelper, encodeServiceResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static ServiceRequest encodeDemo(MSPHelper mspHelper) throws EncodeDecodeException {
        ServiceRequest request = new ServiceRequest<Map<String, Object>>();
        request.setMerchantNo("kltb101");
        request.setAppId("10101");
        request.setRequestFlowNo("10101" + System.currentTimeMillis());
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("userName", "张三");
        paramMap.put("certNo", "430426199010208888");
        paramMap.put("mobile", "18676756101");
        request.setBizObject(paramMap);

        mspHelper.encode(request);
        System.out.println("encode -> request data: " + JSONObject.toJSONString(request));
        return request;
    }

    public static void decodeDemo(SPHelper spHelper, ServiceRequest request) throws EncodeDecodeException {
        spHelper.decode(request);
        System.out.println("decode -> request data: " + JSONObject.toJSONString(request));
    }

    public static ServiceResponse responseDecodeDemo(SPHelper spHelper) throws EncodeDecodeException {
        ServiceResponse response = new ServiceResponse<Map<String, Object>>();
        response.setCode("200");
        response.setMsg("成功");
        Map<String, Object> bizObj = new HashMap<>();
        bizObj.put("applyNo", "apply001");
        bizObj.put("acceptNo", "accept001");
        response.setBizObject(bizObj);

        spHelper.encode(response);
        System.out.println("decode -> response data: " + JSONObject.toJSONString(response));

        return response;
    }

    public static void responseDecodeDemo(MSPHelper mspHelper, ServiceResponse response) throws EncodeDecodeException {
        mspHelper.decode(response);
        System.out.println("decode -> response data: " + JSONObject.toJSONString(response));
    }
}
