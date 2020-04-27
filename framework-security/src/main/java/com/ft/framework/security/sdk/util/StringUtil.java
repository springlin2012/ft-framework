// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) fieldsfirst
// Source File Name: StringUtil.java

package com.ft.framework.security.sdk.util;

import java.util.Arrays;

public class StringUtil {

    public StringUtil() {}

    public static String fixStringLen(String str, char fillChar, int length) {
        int strLen = str.length();
        if (strLen < length) {
            char chars[] = new char[length];
            Arrays.fill(chars, 0, length - strLen, fillChar);
            System.arraycopy(str.toCharArray(), 0, chars, length - strLen, strLen);
            return new String(chars);
        }
        if (strLen > length)
            str = str.substring(strLen - length);
        return str;
    }

    public static String trim(String str) {
        if (str == null || str.length() == 0)
            return null;
        str = str.trim();
        if (str.length() == 0)
            return null;
        else
            return str;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().equals("");
    }

    public static String nullToEmpty(Object str) {
        if (str instanceof String) {
            return str != null ? (String)str : "";
        } else {
            return str != null ? str.toString() : "";
        }
    }
}
