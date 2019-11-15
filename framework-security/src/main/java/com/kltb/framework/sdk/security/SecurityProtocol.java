package com.kltb.framework.sdk.security;

import com.kltb.framework.common.dto.*;
import com.kltb.framework.common.enums.EncryptTypeEnum;
import com.kltb.framework.sdk.exception.EncodeDecodeException;
import com.kltb.framework.sdk.util.AESUtil;
import com.kltb.framework.sdk.util.AsymmetricUtil;
import com.kltb.framework.sdk.util.Base64;
import com.kltb.framework.sdk.util.StringUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

    public SecurityProtocol(SecurityKeyInfo securityKeyInfo)
        throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        this.init(securityKeyInfo);
    }

    private void init(SecurityKeyInfo securityKeyInfo)
        throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        publicKey = AsymmetricKeyHelper.getInstance().readRSAPublicKey(securityKeyInfo.getPublicKeyName(),
            securityKeyInfo.getPublicKeyPath());
        privateKey = AsymmetricKeyHelper.getInstance().readRSAPrivateKey(securityKeyInfo.getPrivateKeyName(),
            securityKeyInfo.getPrivateKeyPath());
    }

}
