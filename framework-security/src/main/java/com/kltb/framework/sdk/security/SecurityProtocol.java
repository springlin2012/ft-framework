package com.kltb.framework.sdk.security;

import com.kltb.framework.common.entity.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

/**
 * @descript: 安全协议处理
 * @auth: lichunlin
 * @date: 2019/11/07.
 */
public class SecurityProtocol {
    protected static String DEFUALT_ENDCODING = "UTF-8";
    protected static PublicKey publicKey = null;
    protected static PrivateKey privateKey = null;

    public SecurityProtocol(SKeyInfo sKeyInfo)
        throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        this.init(sKeyInfo);
    }

    private void init(SKeyInfo sKeyInfo)
        throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        publicKey = AsymmetricKeyHelper.getInstance().readRSAPublicKey(sKeyInfo.getPublicKeyName(),
            sKeyInfo.getPublicKeyPath());
        privateKey = AsymmetricKeyHelper.getInstance().readRSAPrivateKey(sKeyInfo.getPrivateKeyName(),
            sKeyInfo.getPrivateKeyPath());
    }

}
