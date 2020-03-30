package com.kltb.framework.security.sdk.util;

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

/**
 * @descript: 签名工具类
 * @date: 2019/11/06.
 */
public class AsymmetricUtil {
    public static final String DEFAULT_SIGN_ALGORITHM = "SHA256withRSA";
    public static final String DEFAULT_ALGORITHM = "RSA";


    public AsymmetricUtil() {
    }

    public static byte[] genSignature(byte[] input, PrivateKey key, String signAlgorithm) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, NoSuchProviderException {
        signAlgorithm = StringUtil.isEmpty(signAlgorithm) ? DEFAULT_SIGN_ALGORITHM : signAlgorithm;
        Signature sig = Signature.getInstance(signAlgorithm);
        sig.initSign(key);
        sig.update(input);
        return sig.sign();
    }

    public static byte[] genSignature(InputStream input, PrivateKey key, String signAlgorithm) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        signAlgorithm = StringUtil.isEmpty(signAlgorithm) ? DEFAULT_SIGN_ALGORITHM : signAlgorithm;
        Signature sig = Signature.getInstance(signAlgorithm);
        sig.initSign(key);
        byte[] buffer = new byte[1024];

        int n;
        while((n = input.read(buffer)) >= 0) {
            sig.update(buffer, 0, n);
        }

        return sig.sign();
    }

    /**
     * 验证签名
     * @param plainInput
     * @param signedInput
     * @param key
     * @param signAlgorithm
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verifySignature(byte[] plainInput, byte[] signedInput, PublicKey key, String signAlgorithm) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        signAlgorithm = StringUtil.isEmpty(signAlgorithm) ? DEFAULT_SIGN_ALGORITHM : signAlgorithm;
        Signature sig = Signature.getInstance(signAlgorithm);
        sig.initVerify(key);
        sig.update(plainInput);
        boolean verifies = sig.verify(signedInput);
        return verifies;
    }

    public static boolean verifySignatureSha256WithRSA(byte[] plainInput, byte[] signedInput, PublicKey key) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sig = Signature.getInstance(DEFAULT_SIGN_ALGORITHM);
        sig.initVerify(key);
        sig.update(plainInput);
        boolean verifies = sig.verify(signedInput);
        return verifies;
    }

    public static boolean verifySignature(InputStream plainInput, byte[] signedInput, PublicKey key, String signAlgorithm) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        signAlgorithm = StringUtil.isEmpty(signAlgorithm) ? DEFAULT_SIGN_ALGORITHM : signAlgorithm;
        Signature sig = Signature.getInstance(signAlgorithm);
        sig.initVerify(key);
        byte[] buffer = new byte[1024];

        int n;
        while((n = plainInput.read(buffer)) >= 0) {
            sig.update(buffer, 0, n);
        }

        boolean verifies = sig.verify(signedInput);
        return verifies;
    }

    public static byte[] encryptData(byte[] dataToEncrypt, PublicKey publicKey, String encryptAlgorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        encryptAlgorithm = StringUtil.isEmpty(encryptAlgorithm) ? DEFAULT_ALGORITHM : encryptAlgorithm;
        Cipher cipher = Cipher.getInstance(encryptAlgorithm);
        cipher.init(1, publicKey);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ByteArrayInputStream bin = new ByteArrayInputStream(dataToEncrypt);
        byte[] buffer = new byte[117];
        int read = 0;
        for(read = bin.read(buffer); read > 0; read = bin.read(buffer)) {
            if (read < 117) {
                bout.write(cipher.doFinal(Arrays.copyOfRange(buffer, 0, read)));
            } else {
                bout.write(cipher.doFinal(buffer));
            }
        }

        bin.close();
        bout.close();
        return bout.toByteArray();
    }

    public static void encryptData(InputStream dataToEncrypt, OutputStream encryptedOut, PublicKey publicKey, String encryptAlgorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        encryptAlgorithm = StringUtil.isEmpty(encryptAlgorithm) ? DEFAULT_ALGORITHM : encryptAlgorithm;
        Cipher cipher = Cipher.getInstance(encryptAlgorithm);
        cipher.init(1, publicKey);
        byte[] buffer = new byte[117];
        int read = 0;

        for(read = dataToEncrypt.read(buffer); read > 0; read = dataToEncrypt.read(buffer)) {
            if (read < 117) {
                encryptedOut.write(cipher.doFinal(Arrays.copyOfRange(buffer, 0, read)));
            } else {
                encryptedOut.write(cipher.doFinal(buffer));
            }
        }

        dataToEncrypt.close();
        encryptedOut.close();
    }

    public static void decryptData(InputStream dataToDecrypt, OutputStream decryptOut, PrivateKey privateKey, String encryptAlgorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        encryptAlgorithm = StringUtil.isEmpty(encryptAlgorithm) ? DEFAULT_ALGORITHM : encryptAlgorithm;
        Cipher cipher = Cipher.getInstance(encryptAlgorithm);
        cipher.init(2, privateKey);
        byte[] buffer = new byte[128];
        int read = 0;

        for(read = dataToDecrypt.read(buffer); read > 0; read = dataToDecrypt.read(buffer)) {
            if (read < 128) {
                decryptOut.write(cipher.doFinal(Arrays.copyOfRange(buffer, 0, read)));
            } else {
                decryptOut.write(cipher.doFinal(buffer));
            }
        }

        dataToDecrypt.close();
        decryptOut.close();
    }

    public static byte[] decryptData(byte[] data, PrivateKey privateKey, String encryptAlgorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        encryptAlgorithm = StringUtil.isEmpty(encryptAlgorithm) ? DEFAULT_ALGORITHM : encryptAlgorithm;
        Cipher cipher = Cipher.getInstance(encryptAlgorithm);
        cipher.init(2, privateKey);
        byte[] buffer = new byte[128];
        int read = 0;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ByteArrayInputStream bin = new ByteArrayInputStream(data);

        for(read = bin.read(buffer); read > 0; read = bin.read(buffer)) {
            if (read < 128) {
                bout.write(cipher.doFinal(Arrays.copyOfRange(buffer, 0, read)));
            } else {
                bout.write(cipher.doFinal(buffer));
            }
        }

        bin.close();
        bout.close();
        return bout.toByteArray();
    }

    public static PublicKey readRSAPublicKey(String name, byte[] keyBytes) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        InputStream fis = null;
        fis = new ByteArrayInputStream(keyBytes);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        StringBuilder builder = new StringBuilder();
        boolean inKey = false;

        for(String line = br.readLine(); line != null; line = br.readLine()) {
            line = line.trim();
            if (!inKey) {
                if (line.startsWith("-----BEGIN ") && line.endsWith(" PUBLIC KEY-----")) {
                    inKey = true;
                }
            } else {
                if (line.startsWith("-----END ") && line.endsWith(" PUBLIC KEY-----")) {
                    inKey = false;
                    break;
                }

                builder.append(line);
            }
        }

        fis.close();
        keyBytes = Base64.base64ToByteArray(builder.toString());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(DEFAULT_ALGORITHM);
        PublicKey key = kf.generatePublic(spec);
        return key;
    }

    public static PrivateKey readRSAPrivateKey(String name, byte[] keyBytes) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        InputStream fis = new ByteArrayInputStream(keyBytes);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        StringBuilder builder = new StringBuilder();
        boolean inKey = false;

        for(String line = br.readLine(); line != null; line = br.readLine()) {
            line = line.trim();
            if (!inKey) {
                if (line.startsWith("-----BEGIN ") && line.endsWith(" PRIVATE KEY-----")) {
                    inKey = true;
                }
            } else {
                if (line.startsWith("-----END ") && line.endsWith(" PRIVATE KEY-----")) {
                    inKey = false;
                    break;
                }

                builder.append(line);
            }
        }

        fis.close();
        keyBytes = Base64.base64ToByteArray(builder.toString());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(DEFAULT_ALGORITHM);
        PrivateKey key = kf.generatePrivate(spec);
        return key;
    }
}
