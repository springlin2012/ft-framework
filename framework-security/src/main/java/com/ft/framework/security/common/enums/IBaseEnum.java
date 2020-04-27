package com.ft.framework.security.common.enums;

public interface IBaseEnum<T> extends IEnum {

    /**
     * 重置消息
     * @param value
     */
    public abstract T setValue(String value);

    /**
     * key相等判断
     * @param key
     * @return
     */
    public abstract boolean isSuccess(String key);

}
