package com.fjsmu.comm.util;

import org.apache.log4j.Logger;

/**
 * 自定义Loggger， 记录session id
 *
 * Created by zzh on 17/1/13.
 */
public class LoggerUtil {

    private Logger logger;

    public LoggerUtil(Logger logger) {
        this.logger  = logger;
    }

    public void info(Object message) {
        this.logger.info(getSessionIdStr(message));
    }

    public void warn(Object message) {
        this.logger.warn(getSessionIdStr(message));
    }

    public void error(Object message) {
        this.logger.error(getSessionIdStr(message));
    }

    public void debug(Object message){
        this.logger.debug(getSessionIdStr(message));
    }

    private String getSessionIdStr(Object message){
        return "session_id : " + SessionUtil.getSessionId() + " " + message;
    }

}
