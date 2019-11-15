package com.kltb.framework.common.dto;

public class SecurityKeyInfo {
    /**
     * 平台私钥
     */
    private String privateKeyName;
    private String privateKeyPath;

    /**
     * 商户公钥
     */
    private String publicKeyName;
    private String publicKeyPath;

    public SecurityKeyInfo() {
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getPublicKeyPath() {
        return publicKeyPath;
    }

    public void setPublicKeyPath(String publicKeyPath) {
        this.publicKeyPath = publicKeyPath;
    }

    public String getPrivateKeyName() {
        return privateKeyName;
    }

    public void setPrivateKeyName(String privateKeyName) {
        this.privateKeyName = privateKeyName;
    }

    public String getPublicKeyName() {
        return publicKeyName;
    }

    public void setPublicKeyName(String publicKeyName) {
        this.publicKeyName = publicKeyName;
    }
}
