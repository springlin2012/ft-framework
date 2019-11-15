package com.kltb.framework.sdk.util;

import javax.crypto.*;
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
 * @descript: 对称加密工具类
 * @auth: lichunlin
 * @date: 2019/11/06.
 */
public class AESUtil {
    private static final String AES = "AES";
    private static final String AES_CHARSET_NAME = "UTF-8";
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";

    public AESUtil() {}

    public static byte[] encrypt(byte key[], byte ivByte[], byte value[]) throws Exception {
        try {
            SecureRandom sr = new SecureRandom();
            SecretKey securekey = new SecretKeySpec(key, "AES");
            IvParameterSpec iv = new IvParameterSpec(ivByte);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(1, securekey, iv, sr);
            return cipher.doFinal(value);
        } catch (Exception e) {
            throw e;
        }
    }

    public static byte[] encrypt(byte key[], byte value[]) throws Exception {
        try {
            SecretKey securekey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, securekey);
            return cipher.doFinal(value);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void encrypt(byte key[], byte ivByte[], InputStream dataToEncrypt, OutputStream encryptedOut)
        throws Exception {
        SecureRandom sr = new SecureRandom();
        SecretKey securekey = new SecretKeySpec(key, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivByte);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, securekey, iv, sr);
        try {
            byte buffer[] = new byte[1024];
            int read = 0;
            for (read = dataToEncrypt.read(buffer); read > 0; read = dataToEncrypt.read(buffer))
                if (read < 1024)
                    encryptedOut.write(cipher.doFinal(Arrays.copyOfRange(buffer, 0, read)));
                else
                    encryptedOut.write(cipher.doFinal(buffer));

            dataToEncrypt.close();
            encryptedOut.close();
        } catch (Exception e) {
            throw e;
        }
    }

    public static byte[] decrypt(byte key[], byte ivByte[], byte value[]) throws Exception {
        try {
            SecureRandom sr = new SecureRandom();
            SecretKey securekey = new SecretKeySpec(key, "AES");
            IvParameterSpec iv = new IvParameterSpec(ivByte);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, securekey, iv, sr);
            return cipher.doFinal(value);
        } catch (Exception e) {
            throw e;
        }
    }

    public static byte[] decrypt(byte key[], byte value[]) throws Exception {
        try {
            SecretKey securekey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, securekey);
            return cipher.doFinal(value);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void decrypt(byte key[], byte ivByte[], InputStream dataToDecrypt, OutputStream decryptOut)
        throws Exception {
        try {
            SecureRandom sr = new SecureRandom();
            SecretKey securekey = new SecretKeySpec(key, "AES");
            IvParameterSpec iv = new IvParameterSpec(ivByte);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, securekey, iv, sr);
            byte buffer[] = new byte[1040];
            int read = 0;
            for (read = dataToDecrypt.read(buffer); read > 0; read = dataToDecrypt.read(buffer))
                if (read < 1040)
                    decryptOut.write(cipher.doFinal(Arrays.copyOfRange(buffer, 0, read)));
                else
                    decryptOut.write(cipher.doFinal(buffer));

            dataToDecrypt.close();
            decryptOut.close();
        } catch (Exception e) {
            throw e;
        }
    }

    public static String byte2Hex(byte b[]) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xff);
            if (stmp.length() == 1)
                hs = (new StringBuilder(String.valueOf(hs))).append("0").append(stmp).toString();
            else
                hs = (new StringBuilder(String.valueOf(hs))).append(stmp).toString();
            if (n < b.length - 1)
                hs = (new StringBuilder(String.valueOf(hs))).append(":").toString();
        }

        return hs.toUpperCase();
    }

    public static byte[] generateKey(int length) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(length);
        SecretKey key = keyGenerator.generateKey();
        return key.getEncoded();
    }

    public static byte[] generateRandomIV() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(new SecureRandom());
        SecretKey randomDESKey = keyGen.generateKey();
        return randomDESKey.getEncoded();
    }

    public static CipherOutputStream encryptMode(String aesKey, OutputStream outputStream)
        throws NoSuchAlgorithmException {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(aesKey.getBytes());
        kgen.init(128, random);
        SecretKey secretKey = kgen.generateKey();
        byte enCodeFormat[] = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        return encryptMode(((SecretKey)(key)), outputStream);
    }

    public static CipherInputStream decryptMode(String aesKey, InputStream inputStream)
        throws NoSuchAlgorithmException {
        SecretKeySpec keySpec = null;
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(aesKey.getBytes());
        kgen.init(128, random);
        SecretKey secretKey = kgen.generateKey();
        byte encodeKey[] = secretKey.getEncoded();
        keySpec = new SecretKeySpec(encodeKey, "AES");
        return decryptMode(((SecretKey)(keySpec)), inputStream);
    }

    public static CipherInputStream decryptMode(SecretKey secretKey, InputStream inputStream) {
        try {
            Cipher e = Cipher.getInstance("AES");
            e.init(2, secretKey);
            return new CipherInputStream(inputStream, e);
        } catch (Exception var3) {
            return null;
        }
    }

    public static CipherOutputStream encryptMode(SecretKey secretKey, OutputStream outputStream) {
        try {
            Cipher e = Cipher.getInstance("AES");
            e.init(1, secretKey);
            return new CipherOutputStream(outputStream, e);
        } catch (Exception var3) {
            return null;
        }
    }

    public static void main(String args[]) throws Exception {
        byte default_Key[] = generateKey(128);
        String x = new String(default_Key, "UTF-8");
        byte Key[] = x.getBytes("UTF-8");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println((new StringBuilder("***********************************************"))
            .append(Base64.byteArrayToBase64(default_Key)).toString());
        System.out.println("**********\u5BC6\u7801\u52A0\u5BC6\u5C0F\u5DE5\u5177   ***************");
        System.out.print("*** \u8BF7\u8F93\u5165\u5BC6\u7801\u539F\u6587\uFF1A");
    }
}
