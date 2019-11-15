package com.kltb.framework.common.dto;

import com.alibaba.fastjson.JSONObject;

/**
 * @descript: 服务请求属性类
 * @auth: lichunlin
 * @date: 2019/11/07.
 * @param <T>
 */
public class ServiceRequest<T> extends CommonServiceRequest {
    private static final long serialVersionUID = -5916860153373310105L;

    public void setBizObject(T bizObject) {
        this.setBizData(JSONObject.toJSONString(bizObject));
    }
}
