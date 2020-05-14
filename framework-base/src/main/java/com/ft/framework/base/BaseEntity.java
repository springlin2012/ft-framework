package com.ft.framework.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ft.framework.skeleton.PaginatorSevReq;

import java.io.Serializable;

/**
 * <p>
 * 功能描述：实体类基类<BR>
 * 功能说明：重载toString方法，可将各属性转为String输出，便于日志打印
 * </p>
 * 
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 8107118454683536468L;

    @Override
    public String toString() {
        return JSON.toJSONString(this, new SerializerFeature[]{SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.UseISO8601DateFormat});
    }

    public static void main(String[] args) {
        PaginatorSevReq req = new PaginatorSevReq();
        req.setOrder(23);
        System.out.println(JSON.toJSONString(req));
    }
}
