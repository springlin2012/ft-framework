/*
 * Copyright (c) 2019 KLTB, Inc. All rights reserved.
 *
 * @author lichunlin
 */
package com.kltb.framework.sdk.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class RSAUtil {
    public static final String DEFAULT_SIGN_ALGORITHM = "SHA256withRSA";
    public static final String DEFAULT_ENCRYPT_ALGORITHM = "RSA";

    public RSAUtil() {}

    public static byte[] genSignature(byte[] input, PrivateKey key, String signAlgorithm)
        throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, NoSuchProviderException {
        signAlgorithm = StringUtil.isEmpty(signAlgorithm) ? "SHA256withRSA" : signAlgorithm;
        Signature sig = Signature.getInstance(signAlgorithm);
        sig.initSign(key);
        sig.update(input);
        return sig.sign();
    }

    public static byte[] genSignatureWithMD5(byte[] input, PrivateKey key)
        throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, NoSuchProviderException {
        Signature sig = Signature.getInstance("MD5withRSA");
        sig.initSign(key);
        sig.update(input);
        return sig.sign();
    }

    public static byte[] genSignature(InputStream input, PrivateKey key, String signAlgorithm)
        throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        signAlgorithm = StringUtil.isEmpty(signAlgorithm) ? "SHA256withRSA" : signAlgorithm;
        Signature sig = Signature.getInstance(signAlgorithm);
        sig.initSign(key);
        byte[] buffer = new byte[1024];

        int n;
        while ((n = input.read(buffer)) >= 0) {
            sig.update(buffer, 0, n);
        }

        return sig.sign();
    }

    public static boolean verifySignature(byte[] plainInput, byte[] signedInput, PublicKey key, String signAlgorithm)
        throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        signAlgorithm = StringUtil.isEmpty(signAlgorithm) ? "SHA256withRSA" : signAlgorithm;
        Signature sig = Signature.getInstance(signAlgorithm);
        sig.initVerify(key);
        sig.update(plainInput);
        boolean verifies = sig.verify(signedInput);
        return verifies;
    }

    public static boolean verifySignatureSha256WithRSA(byte[] plainInput, byte[] signedInput, PublicKey key)
        throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(key);
        sig.update(plainInput);
        boolean verifies = sig.verify(signedInput);
        return verifies;
    }

    public static boolean verifySignature(InputStream plainInput, byte[] signedInput, PublicKey key,
        String signAlgorithm) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        signAlgorithm = StringUtil.isEmpty(signAlgorithm) ? "SHA256withRSA" : signAlgorithm;
        Signature sig = Signature.getInstance(signAlgorithm);
        sig.initVerify(key);
        byte[] buffer = new byte[1024];

        int n;
        while ((n = plainInput.read(buffer)) >= 0) {
            sig.update(buffer, 0, n);
        }

        boolean verifies = sig.verify(signedInput);
        return verifies;
    }

    public static byte[] encryptData(byte[] dataToEncrypt, PublicKey publicKey, String encryptAlgorithm)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
        BadPaddingException {
        encryptAlgorithm = StringUtil.isEmpty(encryptAlgorithm) ? "RSA" : encryptAlgorithm;
        Cipher cipher = Cipher.getInstance(encryptAlgorithm);
        cipher.init(1, publicKey);
        return cipher.doFinal(dataToEncrypt);
    }

    public static void encryptData(InputStream dataToEncrypt, OutputStream encryptedOut, PublicKey publicKey,
        String encryptAlgorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
        IOException, IllegalBlockSizeException, BadPaddingException {
        encryptAlgorithm = StringUtil.isEmpty(encryptAlgorithm) ? "RSA" : encryptAlgorithm;
        Cipher cipher = Cipher.getInstance(encryptAlgorithm);
        cipher.init(1, publicKey);
        byte[] buffer = new byte[117];
        int read = 0;
        for (read = dataToEncrypt.read(buffer); read > 0; read = dataToEncrypt.read(buffer)) {
            if (read < 117) {
                encryptedOut.write(cipher.doFinal(Arrays.copyOfRange(buffer, 0, read)));
            } else {
                encryptedOut.write(cipher.doFinal(buffer));
            }
        }

        dataToEncrypt.close();
        encryptedOut.close();
    }

    public static void decryptData(InputStream dataToDecrypt, OutputStream decryptOut, PrivateKey privateKey,
        String encryptAlgorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
        IOException, IllegalBlockSizeException, BadPaddingException {
        encryptAlgorithm = StringUtil.isEmpty(encryptAlgorithm) ? "RSA" : encryptAlgorithm;
        Cipher cipher = Cipher.getInstance(encryptAlgorithm);
        cipher.init(2, privateKey);
        byte[] buffer = new byte[128];
        int read = 0;
        for (read = dataToDecrypt.read(buffer); read > 0; read = dataToDecrypt.read(buffer)) {
            if (read < 128) {
                decryptOut.write(cipher.doFinal(Arrays.copyOfRange(buffer, 0, read)));
            } else {
                decryptOut.write(cipher.doFinal(buffer));
            }
        }

        dataToDecrypt.close();
        decryptOut.close();
    }

    public static byte[] decryptData(byte[] data, PrivateKey privateKey, String encryptAlgorithm)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
        BadPaddingException {
        encryptAlgorithm = StringUtil.isEmpty(encryptAlgorithm) ? "RSA" : encryptAlgorithm;
        Cipher cipher = Cipher.getInstance(encryptAlgorithm);
        cipher.init(2, privateKey);
        return cipher.doFinal(data);
    }

    public static PublicKey readRSAPublicKey(String name, byte[] keyBytes)
        throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        InputStream fis = null;
        fis = new ByteArrayInputStream(keyBytes);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        StringBuilder builder = new StringBuilder();

        for (String line = br.readLine(); line != null; line = br.readLine()) {
            line = line.trim();
            if ((!line.startsWith("-----BEGIN ") || !line.endsWith(" PUBLIC KEY-----"))
                && (!line.startsWith("-----END ") || !line.endsWith(" PUBLIC KEY-----"))) {
                builder.append(line);
            }
        }

        fis.close();
        keyBytes = Base64.base64ToByteArray(builder.toString());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey key = kf.generatePublic(spec);
        return key;
    }

    public static PrivateKey readRSAPrivateKey(String name, byte[] keyBytes)
        throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        InputStream fis = new ByteArrayInputStream(keyBytes);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        StringBuilder builder = new StringBuilder();

        for (String line = br.readLine(); line != null; line = br.readLine()) {
            if ((!line.startsWith("-----BEGIN ") || !line.endsWith(" PRIVATE KEY-----"))
                && (!line.startsWith("-----END ") || !line.endsWith(" PRIVATE KEY-----"))) {
                builder.append(line);
            }
        }

        fis.close();
        keyBytes = Base64.base64ToByteArray(builder.toString());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey key = kf.generatePrivate(spec);
        return key;
    }
}
