
package com.ft.framework.security.sdk.util;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class ParamUtil {

    public ParamUtil() {}

    public static Map requestMap2Map(Map requestMap) {
        Map map = new HashMap();
        for (Iterator it = requestMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry)it.next();
            String value[] = (String[])entry.getValue();
            if (value != null && value.length > 0)
                map.put((String)entry.getKey(), value[0]);
        }

        return map;
    }

    public static String sortContentMap(Map contentMap) {
        if (contentMap == null)
            return null;
        Set keySet = contentMap.keySet();
        String keysArr[] = (String[])keySet.toArray(new String[0]);
        Arrays.sort(keysArr);
        StringBuilder signedContent = new StringBuilder();
        String as[];
        int j = (as = keysArr).length;
        for (int i = 0; i < j; i++) {
            String key = as[i];
            if (!key.equals("sign") && !key.equals("signed")) {
                Object object = contentMap.get(key);
                signedContent.append(key).append("=").append(object != null ? String.valueOf(object) : "").append("&");
            }
        }

        String signedContentStr = signedContent.toString();
        if (signedContentStr.endsWith("&"))
            signedContentStr = signedContentStr.substring(0, signedContentStr.length() - 1);
        return signedContentStr;
    }

    public static String sortContentMapByValue(Map contentMap) {
        if (contentMap == null)
            return null;
        Collection values = contentMap.values();
        String valueArr[] = (String[])values.toArray(new String[0]);
        Arrays.sort(valueArr);
        StringBuilder contentBuilder = new StringBuilder();
        String as[];
        int j = (as = valueArr).length;
        for (int i = 0; i < j; i++) {
            String value = as[i];
            contentBuilder.append(value);
        }

        return contentBuilder.toString();
    }

    public static String sortContentMapAndRemoveNull(Map contentMap) {
        if (contentMap == null)
            return null;
        Set keySet = contentMap.keySet();
        String keysArr[] = (String[])keySet.toArray(new String[0]);
        Arrays.sort(keysArr);
        StringBuilder signedContent = new StringBuilder();
        String as[];
        int j = (as = keysArr).length;
        for (int i = 0; i < j; i++) {
            String key = as[i];
            if (!key.equals("signature") && !key.equals("signed")) {
                Object object = contentMap.get(key);
                if (object != null && !StringUtil.isEmpty(object.toString()))
                    signedContent.append(key).append("=").append(object.toString()).append("&");
            }
        }

        String signedContentStr = signedContent.toString();
        if (signedContentStr.endsWith("&"))
            signedContentStr = signedContentStr.substring(0, signedContentStr.length() - 1);
        return signedContentStr;
    }

    public static String getUrlParamsByMap(Map map) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); sb.append("&")) {
            Map.Entry entry = (Map.Entry)iterator.next();
            sb.append((new StringBuilder(String.valueOf((String)entry.getKey()))).append("=").append(entry.getValue())
                .toString());
        }

        String s = sb.toString();
        if (s.endsWith("&"))
            s = StringUtils.substringBeforeLast(s, "&");
        return s;
    }
}
