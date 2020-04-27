package com.ft.framework.skeleton;

import com.ft.framework.base.BaseEntity;

/**
 * 功能描述: 基础业务对象层 (Business Object)
 * 创建人: chunlin.li
 */
public class BaseBO extends BaseEntity {

    private static final long serialVersionUID = 5384669617293453188L;

    /**
     * 业务系统编号
     */
    private String partnerId;

    /**
     * 用户ID
     */
    private String userId;


    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
