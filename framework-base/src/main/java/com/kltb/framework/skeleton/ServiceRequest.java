package com.kltb.framework.skeleton;

import com.kltb.framework.base.BaseEntity;


/**
 * 描述：服务请求通用参数
 */
public class ServiceRequest extends BaseEntity {

    private static final long serialVersionUID = -5167427901498368829L;

    /**
	 * 系统来源
	 */
	private String sysSource;

    /**
     * 来源
     */
    private String clientIP;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 业务系统编号
     */
    private String partnerId;

	public String getSysSource() {
		return sysSource;
	}

	public void setSysSource(String sysSource) {
		this.sysSource = sysSource;
	}

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
