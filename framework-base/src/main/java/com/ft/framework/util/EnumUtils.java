package com.ft.framework.util;

import com.ft.framework.enums.IEnum;
import com.ft.framework.enums.KvmEnum;

/**
 * @descript: 授信业务请求处理
 * @auth: lichunlin
 */
public class EnumUtils {


    /**
     * 根据key获取枚举值
     *
     * @param key   键
     * @param enums 枚举
     * @return
     */
    public static IEnum getEnumByKey(int key, Enum<? extends IEnum>[] enums) {
        for (Enum<? extends IEnum> e : enums) {
            IEnum temp = (IEnum) e;
            if (temp.getKey().equals(key)) {
                return temp;
            }
        }
        return null;
    }

    /**
     * 根据key获取枚举值
     *
     * @param key      键
     * @param enumType 枚举类型
     * @return
     */
    public static <T extends IEnum> T getEnumByKey(int key, Class<T> enumType) {
        if (enumType == null) {
            return null;
        }
        T[] enums = enumType.getEnumConstants();
        for (T e : enums) {
            T temp = (T) e;
            if (temp.getKey().equals(key)) {
                return temp;
            }
        }
        return null;
    }

    public static <T extends IEnum> T getEnumByKey(String key, Class<T> enumType) {
        if (enumType == null) {
            return null;
        }
        T[] enums = enumType.getEnumConstants();
        for (T e : enums) {
            T temp = (T) e;
            if (key.equalsIgnoreCase(String.valueOf(temp.getKey()))) {
                return temp;
            }
        }
        return null;
    }


    /**
     * 根据key获取value
     *
     * @param key 键
     * @return
     */
    public static <T extends IEnum> String getValueByKey(int key, Class<T> enumType) {
        if (enumType == null) {
            return null;
        }
        T[] enums = enumType.getEnumConstants();
        for (T e : enums) {
            T temp = (T) e;
            if (temp.getKey().equals(key)) {
                return temp.getValue();
            }
        }
        return null;
    }

    /**
     * 根据key获取value
     *
     * @param key   键
     * @param enums 枚举
     * @return
     */
    public static String getValueByKey(int key, Enum<? extends IEnum>[] enums) {
        IEnum temp = getEnumByKey(key, enums);
        return temp == null ? "" : temp.getValue();
    }


    /**
     * 根据value获取具体枚举
     *
     * @param value 值
     * @param enums
     * @return
     */
    public static IEnum getEnumByValue(String value, Enum<? extends IEnum>[] enums) {
        for (Enum<? extends IEnum> e : enums) {
            IEnum temp = (IEnum) e;
            if (temp.getValue().equals(value)) {
                return temp;
            }
        }
        return null;
    }

    /**
     * 根据value获取枚举键
     *
     * @param value 值
     * @param enums
     * @return
     */
    public static String getKeyByValue(String value, Enum<? extends IEnum>[] enums) {
        IEnum temp = getEnumByValue(value, enums);
        return temp == null ? "0" : temp.getKey();
    }

    /**
     * 根据key获取枚举值
     *
     * @param key   键
     * @param enums 枚举
     * @return
     */
    public static KvmEnum getKvmEnumByKey(int key, Enum<? extends KvmEnum>[] enums) {
        for (Enum<? extends KvmEnum> e : enums) {
            KvmEnum temp = (KvmEnum) e;
            if (temp.getKey() == key) {
                return temp;
            }
        }
        return null;
    }

    /**
     * 根据key获取value
     *
     * @param key   键
     * @param enums 枚举
     * @return
     */
    public static String getValueByKvmEnumKey(int key, Enum<? extends KvmEnum>[] enums) {
        KvmEnum temp = getKvmEnumByKey(key, enums);
        return temp == null ? "" : temp.getValue();
    }

    /**
     * 根据key获取msg
     *
     * @param key   键
     * @param enums 枚举
     * @return
     */
    public static String getMsgByKvmEnumKey(int key, Enum<? extends KvmEnum>[] enums) {
        KvmEnum temp = getKvmEnumByKey(key, enums);
        return temp == null ? "" : temp.getMsg();
    }

    /**
     * 根据value获取具体枚举
     *
     * @param value 值
     * @param enums
     * @return
     */
    public static KvmEnum getKvmEnumByValue(String value, Enum<? extends KvmEnum>[] enums) {
        for (Enum<? extends KvmEnum> e : enums) {
            KvmEnum temp = (KvmEnum) e;
            if (temp.getValue().equals(value)) {
                return temp;
            }
        }
        return null;
    }

    /**
     * 根据value获取枚举键
     *
     * @param value 值
     * @param enums
     * @return
     */
    public static int getKeyByKvmEnumValue(String value, Enum<? extends KvmEnum>[] enums) {
        KvmEnum temp = getKvmEnumByValue(value, enums);
        return temp == null ? 0 : temp.getKey();
    }

    /**
     * 根据value获取枚msg
     *
     * @param value 值
     * @param enums
     * @return
     */
    public static String getMsgByKvmEnumValue(String value, Enum<? extends KvmEnum>[] enums) {
        KvmEnum temp = getKvmEnumByValue(value, enums);
        return temp == null ? "" : temp.getMsg();
    }


    /**
     * 根据msg获取具体枚举
     *
     * @param msg   值
     * @param enums
     * @return
     */
    public static KvmEnum getKvmEnumByMsg(String msg, Enum<? extends KvmEnum>[] enums) {
        for (Enum<? extends KvmEnum> e : enums) {
            KvmEnum temp = (KvmEnum) e;
            if (temp.getMsg().equals(msg)) {
                return temp;
            }
        }
        return null;
    }

    /**
     * 根据value获取枚举键
     *
     * @param msg   值
     * @param enums
     * @return
     */
    public static int getKeyByKvmEnumMsg(String msg, Enum<? extends KvmEnum>[] enums) {
        KvmEnum temp = getKvmEnumByMsg(msg, enums);
        return temp == null ? 0 : temp.getKey();
    }

    /**
     * 根据value获取枚举键
     *
     * @param msg   值
     * @param enums
     * @return
     */
    public static String getValueByKvmEnumMsg(String msg, Enum<? extends KvmEnum>[] enums) {
        KvmEnum temp = getKvmEnumByMsg(msg, enums);
        return temp == null ? "" : temp.getValue();
    }

}
