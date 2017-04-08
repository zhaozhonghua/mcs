package com.fjsmu.app;


public class ConstUtils {

    public static final String CONTENT_TYPE_UTF8 = "application/json; charset=UTF-8";
    
    /**
     * 分隔符&
     */
    public static final String SPLIT1 = "&";
    
    /**
     * 分隔符*
     */
    public static final String SEWISE_ZHIBOID_SPLIT = "*";

    /**
     * 微信端登陆家长/宝宝/设备/authtoken的cookie的key
     */
    public static final String COOKIE_KEY_AUTHORIZATION_TOKEN = "AuthorizationToken";

    public static final String COOKIE_KEY_PARENTID = "HAIERZYPARENTID";

    /**
     * 用户在线状态心跳秒数, 客户端3s，服务端 +2
     */
    public static final int USER_STATE_HEARTBEAT_SECOND = 5;

    /**
     * 公众号
     */
    public static final int PUBLIC_WECHAT_XUETANG = 1;  // 京师智慧学堂
    public static final int PUBLIC_WECHAT_PA = 2;       // 唷唷推广助手

}
