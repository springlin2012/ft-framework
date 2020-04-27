package com.ft.framework.util;

import org.apache.commons.lang3.StringUtils;
import java.util.UUID;

public class UUIDUtil {

    public static String getUUID_32() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getUUID_32(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return null;
        }
        String rlt = uuid.toString().replaceAll("-", "");
        if (rlt.length() == 32) {
            return rlt;
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.printf(getUUID());
    }

}
