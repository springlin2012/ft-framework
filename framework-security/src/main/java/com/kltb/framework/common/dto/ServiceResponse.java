/*
 * Copyright (c) 2019 KLTB, Inc. All rights reserved.
 *
 * @author lichunlin
 */
package com.kltb.framework.common.dto;

import com.alibaba.fastjson.JSONObject;

/**
 * @descript: 服务请求返回属性类
 * @auth: lichunlin
 * @date: 2019/11/07.
 */
public class ServiceResponse<T> extends CommonServiceResponse {
    private static final long serialVersionUID = -7090178623289693865L;

    private T bizObject;

    public T getBizObject() {
        return bizObject;
    }

    public void setBizObject(T bizObject) {
        this.bizObject = bizObject;
    }

    @Override
    public String toSignContent() {
        this.setBizData(JSONObject.toJSONString(this.bizObject));
        return super.toSignContent();
    }

}
