package com.fjsmu.cache;

/**
 * 短信cache
 *
 * Created by zzh on 16/11/29.
 */
public class SmsCache {

    public static final String SMS_LOGIN_VERIFY_CODE = "sms_login_verify_code_%s";
    public static final String SMS_SALES_AUTH_VERIFY_CODE = "sms_sales_auth_verify_code_%s";
    public static final String SMS_MOBILE_INC = "sms_mobile_inc";

    /**
     * 设置手机号的验证码
     *
     * @param mobile 手机号
     * @param verifyCode 验证码
     * @throws Exception
     */
    public static void setLoginSmsVerifyCode(String mobile, String verifyCode) throws Exception{
        RedisClient.set(String.format(SMS_LOGIN_VERIFY_CODE, mobile), verifyCode, 5 * 60);
    }

    /**
     * 获取该手机号的验证码
     *
     * @param mobile 手机号
     * @return
     * @throws Exception
     */
    public static String getLoginSmsVerifyCode(String mobile) throws Exception{
        return RedisClient.get(String.format(SMS_LOGIN_VERIFY_CODE, mobile));
    }

    /**
     * 设置销售认证手机号的验证码
     *
     * @param mobile 手机号
     * @param verifyCode 验证码
     * @throws Exception
     */
    public static void setSalesAuthSmsVerifyCode(String mobile, String verifyCode) throws Exception{
        RedisClient.set(String.format(SMS_SALES_AUTH_VERIFY_CODE, mobile), verifyCode, 5 * 60);
    }

    /**
     * 获取销售认证该手机号的验证码
     *
     * @param mobile 手机号
     * @return
     * @throws Exception
     */
    public static String getSalesAuthSmsVerifyCode(String mobile) throws Exception{
        return RedisClient.get(String.format(SMS_SALES_AUTH_VERIFY_CODE, mobile));
    }

    /**
     * 清除手机号的验证码
     *
     * @param mobile
     * @throws Exception
     */
    public static void clearLoginSmsVerifyCode(String mobile) throws Exception{
        RedisClient.del(String.format(SMS_LOGIN_VERIFY_CODE, mobile));
    }

    /**
     * 记录手机号今日发送短信次数
     *
     * @param mobile 手机号
     * @throws Exception
     */
    public static void setSmsMobileInc(String mobile) throws Exception{
        RedisClient.hinc(SMS_MOBILE_INC, mobile, 24 * 60 * 60, true);
    }

    /**
     * 获取手机号今日发送短信次数
     *
     * @param mobile 手机号
     * @return
     * @throws Exception
     */
    public static int getSmsMobileInc(String mobile) throws Exception{
        return RedisClient.hgetInt(SMS_MOBILE_INC, mobile);
    }
}
