package com.fjsmu.app;

/**
 * Created by fangcm on 2016/12/27.
 */
public class ErrorCodeException extends Exception {
    public ErrorCode errorCode;

    public ErrorCodeException(ErrorCode errorCode) {
        super(errorCode.errmsg);
        this.errorCode = errorCode;
    }

}
