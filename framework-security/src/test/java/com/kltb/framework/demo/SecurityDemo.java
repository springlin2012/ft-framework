/*
 * Copyright (c) 2019 KLTB, Inc. All rights reserved.
 *
 * @author lichunlin
 */
package com.kltb.ccs.service;

import com.alibaba.fastjson.JSONObject;
import com.kltb.framework.common.entity.SKeyInfo;
import com.kltb.framework.common.entity.ServiceRequest;
import com.kltb.framework.common.entity.ServiceResponse;
import com.kltb.framework.sdk.exception.EncodeDecodeException;
import com.kltb.framework.sdk.security.SecurityHelper;
import com.kltb.framework.sdk.security.MSecurityHelper;
import java.util.HashMap;
import java.util.Map;

/**
 * @descript: 加解密Demo
 * @auth: lichunlin
 */
public class SecurityDemo {

    public static void main(String[] args) {
        try {
            SKeyInfo pKeyInfo = new SKeyInfo();
            pKeyInfo.setPublicKeyName("app_public_key");
            pKeyInfo.setPublicKeyPath("classpath:keys/app_public_key.pem");
            pKeyInfo.setPrivateKeyName("platform_private_key");
            pKeyInfo.setPrivateKeyPath("classpath:keys/platform_private_key.pem");
            SecurityHelper spHelper = new SecurityHelper(pKeyInfo);

            SKeyInfo mKeyInfo = new SKeyInfo();
            mKeyInfo.setPublicKeyName("platform_public_key");
            mKeyInfo.setPublicKeyPath("classpath:keys/platform_public_key.pem");
            mKeyInfo.setPrivateKeyName("app_private_key");
            mKeyInfo.setPrivateKeyPath("classpath:keys/app_private_key.pem");
            MSecurityHelper MSecurityHelper = new MSecurityHelper(mKeyInfo);

            // merchant encode
//             ServiceRequest encodeServiceReq = encodeDemo(MSecurityHelper);
            // platform decode
            decodeDemo(spHelper);

            // platform encode
//            ServiceResponse encodeServiceResponse = responseDecodeDemo(spHelper);
            // merchant decode
//            responseDecodeDemo(mspHelper);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static ServiceRequest encodeDemo(MSecurityHelper MSecurityHelper) throws EncodeDecodeException {
        ServiceRequest request = new ServiceRequest<Map<String, Object>>();
        request.setMerchantNo("kltb101");
        request.setAppId("10101");
        request.setRequestFlowNo("10101" + System.currentTimeMillis());
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userName", "张三");
        paramMap.put("certNo", "430426199010208888");
        paramMap.put("mobile", "18676756101");
        request.setBizObject(paramMap);

        MSecurityHelper.encode(request);
        System.out.println("encode -> request data: " + JSONObject.toJSONString(request));
        return request;
    }

    public static void decodeDemo(SecurityHelper securityHelper) throws EncodeDecodeException {
        ServiceRequest request = JSONObject.parseObject("{\"appId\":\"10101\",\"bizContent\":\"IR8xBgJGKMizNlJoz9c05ICMtXxkXovAHUUi8gkRincvV/bwG5ep29Y0dx/e6tZYRfl35zidniICG81t9+WgGpmLIG63lIHugKhm7gqOC1U=\",\"encoding\":\"UTF-8\",\"encryptKey\":\"nPNFalisynqlhXgdMVP297dmRr2Fi/7hOg26Ky9ug9IxGSbAInOgbtPBsYiLmD/2zmUCuGgTLurt/ZccWc7/7vk8WI7LSs8d6SquDvQYulDsdQ6OriY/SIj2Jvhb6lV6CDJhpzm6HAEaAR5Y1/YUhJzpmqw6QaY2I+X64oSap+M=\",\"encryptType\":\"AES\",\"merchantNo\":\"kltb101\",\"requestFlowNo\":\"101011585292678143\",\"sign\":\"o20jAy8RuVnLW5e3QKtet0bR63hB3bIHXtjbopnzY8Cm5/ka36rRC9Gzb+a1ZBqAWDntVnUAiRWymnWa8rf74MHM0tGZ+Krmjf0Okx6fCaMrUW4rU+avKA2SDKfTOTLDmwdd6kaFGyS+SRW8JtPtrO9VAN6ffeV0I7P8m9f9JiE=\",\"timestamp\":1585292678143}",
                ServiceRequest.class);
        securityHelper.decode(request);
        System.out.println("decode -> request data: " + JSONObject.toJSONString(request));
    }

    public static ServiceResponse responseDecodeDemo(SecurityHelper securityHelper) throws EncodeDecodeException {
        ServiceResponse response = new ServiceResponse<Map<String, Object>>();
        response.setCode("200");
        response.setMsg("成功");
        Map<String, Object> bizObj = new HashMap<>();
        bizObj.put("applyNo", "apply001");
        bizObj.put("acceptNo", "accept001");
        response.setBizObject(bizObj);

        securityHelper.encode(response);
        System.out.println("decode -> response data: " + JSONObject.toJSONString(response));

        return response;
    }

    public static void responseDecodeDemo(MSecurityHelper MSecurityHelper) throws EncodeDecodeException {
        ServiceResponse response = JSONObject.parseObject("{\"bizContent\":\"gATk0dKSgTCp5SqTfzB780S1Us0hVV9h3Uuz62fDAKrAXJWZ6WEmVm48P7xVoQeh\",\"code\":\"200\",\"encoding\":\"UTF-8\",\"encryptKey\":\"d11QbSIuTY+NStt/TJsbc2q1oHTsptVDyuHZ+AwmHg5fHD6y/A4Gg0a5LW9/68mBZZ3wgv9G7Sf8YlEbaq/xocHV/H5C84cuipwNkIX4j7T10WvOafZuSiPff8vdVJto+A0k3W0BdlVhjlbRbC5+aJW3vKwyC6z8zRmo0AxHckE=\",\"encryptType\":\"AES\",\"msg\":\"成功\",\"sign\":\"axqSpR/c8RdcJzEn+huxkZhaGfY2b3RVJcwPp2rT7L3sgFyJd40GTsUPYmtnuyGxiKegVqEGwYVqUAdgRfX0IeFQOlxwmwZH/4wXnSm44pqYrnlUqqmuW2yZwewhA+fSGfsooEyeFHk2FAmKvrg7e6+IUe9WsfmpyzTPqxEXo74=\",\"timestamp\":1585290862745}", ServiceResponse.class);
        MSecurityHelper.decode(response);
        System.out.println("decode -> response data: " + JSONObject.toJSONString(response));
    }
}
