package com.fjsmu.tools;

import org.apache.commons.lang3.*;

public class ConvertUtils {

    public static Integer parseInt(Object value, Integer defaultValue) {
        try {
            return Integer.parseInt(String.valueOf(value));
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public static Long parseLong(Object value, Long defaultValue) {
        try {
            return Long.parseLong(String.valueOf(value));
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public static String object2String(Object object) {
        if (object == null) {
            return null;
        } else {
            return String.valueOf(object);
        }
    }

    public static Boolean parseBoolean(Object value, Boolean defaultValue) {
        try {
            return Boolean.parseBoolean(String.valueOf(value));
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public static boolean startWithHttp(String address) {
        if(StringUtils.startsWithIgnoreCase(address,"http")){
            return true;
        }
        return false;

    }

}
