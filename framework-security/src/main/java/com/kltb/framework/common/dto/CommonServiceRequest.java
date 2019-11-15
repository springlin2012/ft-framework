package com.kltb.framework.common.dto;

import com.kltb.framework.sdk.exception.SignException;
import com.kltb.framework.sdk.util.AsymmetricUtil;
import com.kltb.framework.sdk.util.Base64;
import com.kltb.framework.sdk.util.StringUtil;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;

public class CommonServiceRequest extends BaseEntity {
    private static final long serialVersionUID = 8805190317242772596L;

    /**
     * 商户号
     */
    private String merchantNo;

    /**
     * 商户接入应用ID
     */
    private String appId;

    /**
     * 编码类型
     */
    private String encoding;

    /**
     * 请求时间 yyyy-MM-dd HH:mm:ss
     */
    private String requestDateTime;

    /**
     * 请求流水号,每次请求唯一
     */
    private String requestFlowNo;

    public CommonServiceRequest() {
        encoding = "UTF-8";
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(String requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    public String getRequestFlowNo() {
        return requestFlowNo;
    }

    public void setRequestFlowNo(String requestFlowNo) {
        this.requestFlowNo = requestFlowNo;
    }

    /**
     * 生成签名
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

    private void doSign(PrivateKey privateKey, String signAlgorithm, boolean isUsingAppConfig) throws SignException {
        try {
            this.setSign(Base64.byteArrayToBase64(
                AsymmetricUtil.genSignature(this.toSignContent().getBytes(this.encoding), privateKey, signAlgorithm)));
        } catch (Exception var5) {
            throw new SignException("签名失败", var5);
        }
    }

    /**
     * 组装签名内容
     *
     * @return
     */
    public String toSignContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("appId").append("=").append(StringUtil.nullToEmpty(this.getAppId())).append("&").append("merchantNo")
            .append("=").append(StringUtil.nullToEmpty(this.getMerchantNo())).append("&").append("requestFlowNo")
            .append("=").append(StringUtil.nullToEmpty(this.getRequestFlowNo())).append("&").append("requestDateTime")
            .append("=").append(StringUtil.nullToEmpty(this.getRequestDateTime())).append("&").append("encoding")
            .append("=").append(StringUtil.nullToEmpty(this.getEncoding())).append("&").append("encryptKey").append("=")
            .append(StringUtil.nullToEmpty(this.getEncryptKey())).append("&").append("encryptType").append("=")
            .append(StringUtil.nullToEmpty(this.getEncryptType())).append("&").append("bizData").append("=")
            .append(StringUtil.nullToEmpty(this.getBizData()));
        return sb.toString();
    }

    /**
     * 验签
     *
     * @param publicKey
     * @return
     * @throws SignException
     */
    public boolean verify(PublicKey publicKey) throws SignException {
        try {
            return AsymmetricUtil.verifySignature(this.toSignContent().getBytes(this.encoding),
                Base64.base64ToByteArray(this.getSign()), publicKey, null);
        } catch (Exception e) {
            throw new SignException("签名验证失败", e);
        }
    }

    public static boolean verify(Map<String, String> params, PublicKey publicKey) throws SignException {
        return doVerify(params, publicKey, null, true);
    }

    public static boolean verify(Map<String, String> params, PublicKey publicKey, String signAlgorithm)
        throws SignException {
        return doVerify(params, publicKey, signAlgorithm, false);
    }

    private static boolean doVerify(Map<String, String> params, PublicKey publicKey, String signAlgorithm,
        boolean isUsingAppConfig) throws SignException {
        try {
            String sign = params.get("sign");
            String encoding = params.get("encoding");
            if (StringUtil.isEmpty(encoding)) {
                encoding = "UTF-8";
            }

            Set<String> keySet = params.keySet();
            Iterator<String> keyItr = keySet.iterator();
            ArrayList keyList = new ArrayList();

            while (true) {
                String key;
                do {
                    if (!keyItr.hasNext()) {
                        String[] keysArr = (String[])keyList.toArray(new String[0]);
                        Arrays.sort(keysArr);
                        StringBuilder signedContent = new StringBuilder();

                        for (int i = 0; i < keysArr.length; ++i) {
                            signedContent.append(keysArr[i]).append("=").append(params.get(keysArr[i])).append("&");
                        }

                        String signedContentStr = signedContent.toString();
                        if (signedContentStr.endsWith("&")) {
                            signedContentStr = signedContentStr.substring(0, signedContentStr.length() - 1);
                        }

                        return AsymmetricUtil.verifySignature(signedContentStr.getBytes(encoding),
                            Base64.base64ToByteArray(sign), publicKey, signAlgorithm);
                    }

                    key = keyItr.next();
                } while (!key.equals("appId") && !key.equals("flowNo") && !key.equals("bizContent")
                    && !key.equals("encoding") && !key.equals("reqDateTime") && !key.equals("reqServiceId")
                    && !key.equals("encryptKey") && !key.equals("merchantId") && !key.equals("subMerchantId"));

                keyList.add(key);
            }
        } catch (Exception e) {
            throw new SignException("签名验证失败", e);
        }
    }
}
