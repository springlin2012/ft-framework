/*
 * Copyright (c) 2019 KLTB, Inc. All rights reserved.
 *
 * @author lichunlin
 */
package com.ft.framework.security.sdk.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * @descript: DES加密
 * @date: 2019/11/07.
 */
public class DESUtil {
    private static final String DESEDE = "DESede";
    private static final String DESEDE_CHARSET_NAME = "UTF-8";
    private static final String DES_CBC = "DESede/CBC/PKCS5Padding";

    public DESUtil() {}

    public static byte[] encrypt(byte[] key, byte[] ivByte, byte[] value) throws Exception {
        try {
            SecureRandom sr = new SecureRandom();
            SecretKey securekey = new SecretKeySpec(key, DESEDE);
            IvParameterSpec iv = new IvParameterSpec(ivByte);
            Cipher cipher = Cipher.getInstance(DES_CBC);
            cipher.init(1, securekey, iv, sr);
            return cipher.doFinal(value);
        } catch (Exception var7) {
            throw var7;
        }
    }

    public static void encrypt(byte[] key, byte[] ivByte, InputStream dataToEncrypt, OutputStream encryptedOut)
        throws Exception {
        SecureRandom sr = new SecureRandom();
        SecretKey securekey = new SecretKeySpec(key, DESEDE);
        IvParameterSpec iv = new IvParameterSpec(ivByte);
        Cipher cipher = Cipher.getInstance(DES_CBC);
        cipher.init(1, securekey, iv, sr);

        try {
            byte[] buffer = new byte[1024];
            int read = 0;
            for (read = dataToEncrypt.read(buffer); read > 0; read = dataToEncrypt.read(buffer)) {
                if (read < 1024) {
                    encryptedOut.write(cipher.doFinal(Arrays.copyOfRange(buffer, 0, read)));
                } else {
                    encryptedOut.write(cipher.doFinal(buffer));
                }
            }

            dataToEncrypt.close();
            encryptedOut.close();
        } catch (Exception var10) {
            throw var10;
        }
    }

    public static byte[] decrypt(byte[] key, byte[] ivByte, byte[] value) throws Exception {
        try {
            SecureRandom sr = new SecureRandom();
            SecretKey securekey = new SecretKeySpec(key, DESEDE);
            IvParameterSpec iv = new IvParameterSpec(ivByte);
            Cipher cipher = Cipher.getInstance(DES_CBC);
            cipher.init(2, securekey, iv, sr);
            return cipher.doFinal(value);
        } catch (Exception var7) {
            throw var7;
        }
    }

    public static void decrypt(byte[] key, byte[] ivByte, InputStream dataToDecrypt, OutputStream decryptOut)
        throws Exception {
        try {
            SecureRandom sr = new SecureRandom();
            SecretKey securekey = new SecretKeySpec(key, DESEDE);
            IvParameterSpec iv = new IvParameterSpec(ivByte);
            Cipher cipher = Cipher.getInstance(DES_CBC);
            cipher.init(2, securekey, iv, sr);
            byte[] buffer = new byte[1032];
            int read = 0;

            for (read = dataToDecrypt.read(buffer); read > 0; read = dataToDecrypt.read(buffer)) {
                if (read < 1032) {
                    decryptOut.write(cipher.doFinal(Arrays.copyOfRange(buffer, 0, read)));
                } else {
                    decryptOut.write(cipher.doFinal(buffer));
                }
            }

            dataToDecrypt.close();
            decryptOut.close();
        } catch (Exception var10) {
            throw var10;
        }
    }

    public static String byte2Hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }

            if (n < b.length - 1) {
                hs = hs + ":";
            }
        }

        return hs.toUpperCase();
    }

    public static byte[] generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(DESEDE);
        keyGen.init(new SecureRandom());
        SecretKey key = keyGen.generateKey();
        return key.getEncoded();
    }

    public static byte[] generateRandomIV() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(new SecureRandom());
        SecretKey randomDESKey = keyGen.generateKey();
        return randomDESKey.getEncoded();
    }

    public static void main(String[] args) throws Exception {
        byte[] default_Key = generateKey();
        byte[] Key = generateRandomIV();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("=====密码加密工具=====");
        System.out.print("请输入密码原文：");
        String sSrcPwd = "a5hQ4/1rtiTRKVRbE0iYDg==";

        while (true) {
            sSrcPwd = br.readLine();
            if (sSrcPwd != null && sSrcPwd.length() > 0) {
                byte[] encrypt = encrypt(default_Key, Key, sSrcPwd.getBytes());
                String sPwd = Base64.byteArrayToBase64(encrypt);
                System.out.println("加密后密文：{" + sPwd + "}");
                System.out.println("==================================");
                byte[] decodePwd = Base64.base64ToByteArray(sPwd);
                decodePwd = decrypt(default_Key, Key, decodePwd);
                System.out.println("解密后原文：{" + new String(decodePwd, DESEDE_CHARSET_NAME) + "}");
                System.out.println("==================================");
                return;
            }

            System.out.print("请输入密码原文：");
        }
    }
}
