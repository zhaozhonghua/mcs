package com.fjsmu.comm.util;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import com.fjsmu.app.ConfigUtils;
import com.fjsmu.cache.RedisClient;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 极光推送client发送
 *
 * Created by zzh on 16/11/30.
 */
public class PushUtil {

    private static Logger logger = Logger.getLogger(RedisClient.class);

    public static final int PUSH_TYPE_APP_LOGIN = 1; // app登陆消息通知
    public static final int PUSH_TYPE_SCAN_UPD_STUDENT_INFO = 2; // app扫码修改宝宝信息
    public static final int PUSH_TYPE_LIVE_START_NOTICE = 3; // 直播开始通知
    public static final int PUSH_TYPE_FRIEND_LOGIN_NOTICE = 4; // 好友登陆通知, 宝宝通讯录
    public static final int PUSH_TYPE_SCAN_ADD_STUDENT_INFO = 5; // 扫码添加宝宝通知
    public static final int PUSH_TYPE_SCAN_PUBLISH_STATUS = 6; // 扫码上传状态通知
    public static final int PUSH_TYPE_SCAN_ADD_CLASS_CIRCLE = 7; // 扫码上传班级圈状态
    public static final int PUSH_TYPE_REFRESH_QRCODE = 8; // 刷新app生成的二维码
    public static final int PUSH_TYPE_DYT_PAY = 9; // 新东方课程购买成单通知
    public static final int PUSH_TYPE_SCAN_ADD_FAMILYCONTACTS = 10; // 扫码添加家庭通讯录通知
    public static final int PUSH_TYPE_USER_FORCE_OFFLINE = 11; // 账号登出，踢人通知
    public static final int PUSH_TYPE_MEMBER_PAY_NOTICE = 12; // 购买会员通知

    private static Map<Integer, String> typeMap = new HashMap<Integer, String>();

    private static final String appKey = ConfigUtils.getString("jpush_app_key");
    private static final String masterSecret = ConfigUtils.getString("jpush_master_secret");

    public static final String ALERT = "唷唷兔消息通知";
//    public static final String REGISTRATION_ID = "100d85590948ce74607";

    /**
     * 指定注册Id发送
     *
     * @param jpushRegId 注册ID
     * @param type 类型
     * @param jsonObject 需要app里处理的数据
     */
    private static void androidSendPush(final String jpushRegId, final int type, final JsonObject jsonObject) {

        logger.debug("jpushRegId:"+jpushRegId + ", type:"+type+", data:"+(jsonObject != null ? jsonObject.toString() : "{}"));

        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);

        PushPayload payload = buildPushAndroid(jpushRegId, type, getTypeMap().get(type), jsonObject);
        try {
            PushResult result = jpushClient.sendPush(payload);
            logger.info("Jpush Got result - " + result);

        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);

        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());
            logger.info("Msg ID: " + e.getMsgId());
        }
    }

    /**
     * 构造PushPayload
     *
     * @param jpushRegId
     * @param type
     * @param jsonObject
     * @return
     */
    private static PushPayload buildPushAndroid(final String jpushRegId, final int type, final String msgContent, final JsonObject jsonObject) {
        JSONObject j = new JSONObject();
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(jpushRegId))
//                .setNotification(Notification.newBuilder()
//                        .addPlatformNotification(AndroidNotification.newBuilder()
//                                .setAlert(ALERT)
//                                .addExtra("type", type)
//                                .addExtra("data", jsonObject)
//                                .build())
//                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(msgContent)
                        .addExtra("type", type)
                        .addExtra("data", jsonObject != null ? jsonObject.toString() : "{}" )
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build())
                .build();
    }

    /**
     * 获取push推送描述
     *
     * @return
     */
    private static Map<Integer, String> getTypeMap(){
        typeMap.put(PUSH_TYPE_APP_LOGIN, "App扫码登陆");
        typeMap.put(PUSH_TYPE_SCAN_UPD_STUDENT_INFO, "App扫码修改宝宝信息");
        typeMap.put(PUSH_TYPE_LIVE_START_NOTICE, "直播开始通知");
        typeMap.put(PUSH_TYPE_FRIEND_LOGIN_NOTICE, "好友登陆通知");
        typeMap.put(PUSH_TYPE_SCAN_ADD_STUDENT_INFO, "扫码添加宝宝通知");
        typeMap.put(PUSH_TYPE_SCAN_PUBLISH_STATUS, "扫码上传状态通知");
        typeMap.put(PUSH_TYPE_SCAN_ADD_CLASS_CIRCLE, "扫码上传状态分享班级圈");
        typeMap.put(PUSH_TYPE_REFRESH_QRCODE, "刷新二维码");
        typeMap.put(PUSH_TYPE_DYT_PAY, "新东方课程购买成单通知");
        typeMap.put(PUSH_TYPE_SCAN_ADD_FAMILYCONTACTS, "扫码添加家庭通讯录通知");
        typeMap.put(PUSH_TYPE_USER_FORCE_OFFLINE, "登出后通知另一台设备用户下线");
        typeMap.put(PUSH_TYPE_MEMBER_PAY_NOTICE, "购买会员通知");
        return typeMap;
    }

    /**
     * 发送消息通知
     *
     * @param jpushRegId 极光注册ID
     * @param type 通知类型
     * @param jsonObject 告诉app下一步处理用到的数据
     * @throws Exception
     */
    private static void send(final String jpushRegId,
                            final int type,
                            final JsonObject jsonObject) throws Exception{
        androidSendPush(jpushRegId, type, jsonObject);
    }

}
