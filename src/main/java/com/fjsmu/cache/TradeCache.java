package com.fjsmu.cache;

import com.google.gson.JsonObject;

/**
 * 支付订单缓存
 *
 * Created by zzh on 17/3/21.
 */
public class TradeCache {

    private static final String TRADE_OUT_NO = "trade_%s";

    /**
     * 支付订单缓存信息
     *
     * @param outTradeNo 订单号
     * @param parentId 家长ID
     * @param studentId 宝宝Id
     * @param deviceUUID 设备ID
     * @param memberTypeId 当前购买的会员类型ID
     * @throws Exception
     */
    public static void setTradeInfo(String outTradeNo, String parentId, String studentId,
                                    String deviceUUID, String memberTypeId, String recomId,
                                    int buyType) throws Exception{
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("parentId", parentId);
        jsonObject.addProperty("studentId", studentId);
        jsonObject.addProperty("deviceUUID", deviceUUID);
        jsonObject.addProperty("memberTypeId", memberTypeId);
        jsonObject.addProperty("recomId", recomId);
        jsonObject.addProperty("buyType", buyType);
        RedisClient.set(String.format(TRADE_OUT_NO, outTradeNo), jsonObject.toString(), 24 * 60 * 60);
    }

    /**
     * 获取支付订单缓存信息
     *
     * @param outTradeNo 订单号
     * @return
     * @throws Exception
     */
    public static String getTradeInfo(String outTradeNo) throws Exception{
        return RedisClient.get(String.format(TRADE_OUT_NO, outTradeNo));
    }

}
