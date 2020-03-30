package com.kltb.framework.security.sdk.security;

import com.kltb.framework.security.sdk.util.AsymmetricUtil;
import com.kltb.framework.security.sdk.util.Base64;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @descript: 密钥获取工具类
 * @auth: lichunlin
 * @date: 2019/11/07.
 */
public class AsymmetricKeyHelper {
    private static AsymmetricKeyHelper instance = null;
    private Map<String, PublicKey> publicKeyMap = new HashMap();
    private Map<String, PrivateKey> privateKeyMap = new HashMap();
    private Map<String, String> certMap = new HashMap();

    private AsymmetricKeyHelper() {
    }

    public static AsymmetricKeyHelper getInstance() {
        if (instance != null) {
            return instance;
        } else {
            synchronized (AsymmetricKeyHelper.class) {
                if (instance == null) {
                    instance = new AsymmetricKeyHelper();
                }
            }

            return instance;
        }
    }

    public void addPublicKey(String name, PublicKey key) {
        synchronized (this.publicKeyMap) {
            if (this.publicKeyMap.containsKey(name)) {
                throw new RuntimeException("公钥已经存在，name=" + name);
            } else {
                this.publicKeyMap.put(name, key);
            }
        }
    }

    public void addPrivateKey(String name, PrivateKey key) {
        synchronized (this.privateKeyMap) {
            if (this.privateKeyMap.containsKey(name)) {
                throw new RuntimeException("私钥已经存在，name=" + name);
            } else {
                this.privateKeyMap.put(name, key);
            }
        }
    }

    public PublicKey getPublicKey(String name) {
        synchronized (this.privateKeyMap) {
            return (PublicKey) this.publicKeyMap.get(name);
        }
    }

    public PrivateKey getPrivateKey(String name) {
        synchronized (this.privateKeyMap) {
            return (PrivateKey) this.privateKeyMap.get(name);
        }
    }

    public void addGenKeyPair(String name, String algorithm, int keySize, String providerName, Provider provider) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyGen = null;
        SecureRandom.getInstance(algorithm, providerName);
        SecureRandom random;
        if (provider == null) {
            keyGen = KeyPairGenerator.getInstance(algorithm, providerName);
            random = SecureRandom.getInstance(algorithm, providerName);
        } else {
            keyGen = KeyPairGenerator.getInstance(algorithm, provider);
            random = SecureRandom.getInstance(algorithm, provider);
        }

        keyGen.initialize(keySize, random);
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();
        this.addPrivateKey(name, priv);
        this.addPublicKey(name, pub);
    }

    /**
     * 读取私钥
     * @param name
     * @param filename
     * @param decode
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws InvalidKeySpecException
     */
    public PrivateKey readRSAPrivateKey(String name, String filename, boolean decode) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        PrivateKey key = this.getPrivateKey(name);
        if (key != null) {
            return key;
        } else {
            InputStream fis = null;
            if (filename.indexOf("classpath:") >= 0) {
                filename = filename.replace("classpath:", "");
                if (filename.startsWith("/")) {
                    filename = filename.substring(1);
                }

                fis = AsymmetricKeyHelper.class.getClassLoader().getResourceAsStream(filename);
            } else if (filename.indexOf("file:") >= 0) {
                filename = filename.replace("file:", "");
                fis = new FileInputStream(filename);
            } else {
                fis = new FileInputStream(filename);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((InputStream) fis));
            StringBuilder builder = new StringBuilder();
            boolean inKey = false;

            String line;
            for (line = br.readLine(); line != null; line = br.readLine()) {
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

            ((InputStream) fis).close();
            line = null;
            byte[] keyBytes;
            if (!decode) {
                keyBytes = Base64.base64ToByteArray(builder.toString());
            } else {
                keyBytes = decodeBase64(builder.toString());
            }

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            key = kf.generatePrivate(spec);
            this.addPrivateKey(name, key);
            return key;
        }
    }

    public static byte[] decodeBase64(String str) {
        org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
        return base64.decode(str.getBytes());
    }

    public PrivateKey readRSAPrivateKey(String name, String filename) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        return this.readRSAPrivateKey(name, filename, false);
    }

    public PrivateKey readRSAPrivateKey(String name, byte[] keyBytes) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        PrivateKey key = this.getPrivateKey(name);
        if (key != null) {
            return key;
        } else {
            key = AsymmetricUtil.readRSAPrivateKey(name, keyBytes);
            this.addPrivateKey(name, key);
            return key;
        }
    }

    /**
     * 读取公钥匙
     * @param name
     * @param filename
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws InvalidKeySpecException
     */
    public PublicKey readRSAPublicKey(String name, String filename) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        PublicKey key = this.getPublicKey(name);
        if (key != null) {
            return key;
        } else {
            InputStream fis = null;
            if (filename.indexOf("classpath:") >= 0) {
                filename = filename.replace("classpath:", "");
                if (filename.startsWith("/")) {
                    filename = filename.substring(1);
                }

                fis = AsymmetricKeyHelper.class.getClassLoader().getResourceAsStream(filename);
            } else if (filename.indexOf("file:") >= 0) {
                filename = filename.replace("file:", "");
                fis = new FileInputStream(filename);
            } else {
                fis = new FileInputStream(filename);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((InputStream) fis));
            StringBuilder builder = new StringBuilder();
            boolean inKey = false;

            for (String line = br.readLine(); line != null; line = br.readLine()) {
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

            ((InputStream) fis).close();
            byte[] keyBytes = Base64.base64ToByteArray(builder.toString());
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            key = kf.generatePublic(spec);
            this.addPublicKey(name, key);
            return key;
        }
    }

    public PublicKey readRSAPublicKey(String name, byte[] keyBytes) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        PublicKey key = this.getPublicKey(name);
        if (key != null) {
            return key;
        } else {
            key = AsymmetricUtil.readRSAPublicKey(name, keyBytes);
            this.addPublicKey(name, key);
            return key;
        }
    }

    public String getCert(String name) {
        synchronized (this.certMap) {
            return (String) this.certMap.get(name);
        }
    }

}
