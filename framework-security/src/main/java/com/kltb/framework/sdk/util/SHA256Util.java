/*
 * Copyright (c) 2019 KLTB, Inc. All rights reserved.
 *
 * @author lichunlin
 */
package com.kltb.framework.sdk.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @descript: 哈希工具类
 * @auth: lichunlin
 * @date: 2019/11/07.
 */
public class SHA256Util {
    public SHA256Util() {}

    public static String getSHA256StrJava(String str) {
        if (StringUtil.isEmpty(str)) {
            return null;
        } else {
            String encodeStr = "";

            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                messageDigest.update(str.getBytes("UTF-8"));
                encodeStr = byte2Hex(messageDigest.digest());
            } catch (NoSuchAlgorithmException var4) {
                var4.printStackTrace();
            } catch (UnsupportedEncodingException var5) {
                var5.printStackTrace();
            }

            return encodeStr;
        }
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;

        for (int i = 0; i < bytes.length; ++i) {
            temp = Integer.toHexString(bytes[i] & 255);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }

            stringBuffer.append(temp);
        }

        return stringBuffer.toString();
    }
}
