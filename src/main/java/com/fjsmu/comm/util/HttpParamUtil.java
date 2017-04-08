package com.fjsmu.comm.util;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import java.util.Map;

/**
 * 获取Http参数
 *
 * Created by zzh on 16/11/26.
 */
public class HttpParamUtil {

    private static LoggerUtil logger = new LoggerUtil(org.apache.log4j.Logger.getLogger(HttpParamUtil.class));

    /**
     * 获取用户IP
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        logger.info("getIpAddr : " + ip);
        return ip.split(",")[0];
    }

    /**
     * 获取cookie中对应key的value
     *
     * @param requestContext
     * @param key
     * @return
     * @throws Exception
     */
    public static String getCookieValue(ContainerRequestContext requestContext, String key){
        Map<String, Cookie> map = requestContext.getCookies();
        if(map != null && map.size() > 0){
            Cookie cookie = map.get(key);
            if(cookie != null){
                logger.info("cookie " + key + " : " + cookie.getValue());
                return cookie.getValue();
            }
        }
        return null;
    }

}
