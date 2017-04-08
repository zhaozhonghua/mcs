package com.fjsmu.app;

/**
 * Created by fangcm on 2016/12/27.
 */
public class ErrorCode {
    public static final String ERROR_CODE = "errcode";
    public static final String ERROR_MSG = "errmsg";

    public final int errcode;
    public final String errmsg;


    public ErrorCode(int errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }
}