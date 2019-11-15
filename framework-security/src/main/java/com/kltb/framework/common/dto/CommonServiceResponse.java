package com.kltb.framework.common.dto;

import com.kltb.framework.sdk.exception.SignException;
import com.kltb.framework.sdk.util.AsymmetricUtil;
import com.kltb.framework.sdk.util.Base64;
import com.kltb.framework.sdk.util.StringUtil;
import java.security.PrivateKey;
import java.security.PublicKey;

public class CommonServiceResponse extends BaseEntity {
    private static final long serialVersionUID = 4044162816402468625L;
    private static final String encoding = "UTF-8";
    private String resultCode;
    private String resultMsg;
    private String responseDateTime;


    public CommonServiceResponse() {}

    public String getResponseDateTime() {
        return responseDateTime;
    }

    public void setResponseDateTime(String responseDateTime) {
        this.responseDateTime = responseDateTime;
    }


    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    /**
     * 签名
     *
     * @param privateKey
     * @throws SignException
     */
    public void sign(PrivateKey privateKey) throws SignException {
        this.doSign(privateKey, null, true);
    }

    public void sign(PrivateKey privateKey, String signAlgorithm) throws SignException {
        this.doSign(privateKey, signAlgorithm, false);
    }

    public void doSign(PrivateKey privateKey, String signAlgorithm, boolean isUsingAppConfig) throws SignException {
        try {
            this.setSign(Base64.byteArrayToBase64(
                AsymmetricUtil.genSignature(this.toSignContent().getBytes("UTF-8"), privateKey, signAlgorithm))
            );
        } catch (Exception var5) {
            throw new SignException("签名失败", var5);
        }
    }

    public String toSignContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("bizData").append("=").append(StringUtil.nullToEmpty(this.getBizData())).append("&")
            .append("encryptKey").append("=").append(StringUtil.nullToEmpty(this.getEncryptKey())).append("&")
            .append("encryptType").append("=").append(StringUtil.nullToEmpty(this.getEncryptType())).append("&")
            .append("resultCode").append("=").append(StringUtil.nullToEmpty(this.getResultCode())).append("&")
            .append("resultMsg").append("=").append(StringUtil.nullToEmpty(this.getResultMsg())).append("&")
            .append("responseDateTime").append("=").append(StringUtil.nullToEmpty(this.getResponseDateTime()));
        return sb.toString();
    }

    /**
     * 验签
     *
     * @param publicKey
     * @throws SignException
     */
    public void verify(PublicKey publicKey) throws SignException {
        if (!this.doVerify(publicKey, null, true)) {
            throw new SignException("返回验签失败");
        }
    }

    public boolean verify(PublicKey publicKey, String signAlgorithm) throws SignException {
        return this.doVerify(publicKey, signAlgorithm, false);
    }

    public boolean doVerify(PublicKey publicKey, String signAlgorithm, boolean isUsingAppConfig) throws SignException {
        try {
            return AsymmetricUtil.verifySignature(this.toSignContent().getBytes(encoding),
                Base64.base64ToByteArray(this.getSign()), publicKey, signAlgorithm);
        } catch (Exception var5) {
            throw new SignException("签名验证失败", var5);
        }
    }
}
