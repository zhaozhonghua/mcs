package com.fjsmu.comm.util;

/**
 * 获取当前请求的sessionId
 *
 * Created by zzh on 17/1/12.
 */
public abstract class SessionUtil {

    private static final ThreadLocal<String> sessionIdThreadLocal = new ThreadLocal<String>();

    public static String getSessionId() {
        return sessionIdThreadLocal.get();
    }

    public static void setSessionId(String sessionId) {
        sessionIdThreadLocal.set(sessionId);
    }

}
