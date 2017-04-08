package com.fjsmu.cache;

/**
 * 请求用到Cache
 *
 * Created by zzh on 16/12/5.
 */
public class RequestCache {

    private static final String REQUEST_UUID = "request_uuid";

    public static void setRequestUuid(String uuid){
        RedisClient.hset(REQUEST_UUID, uuid, "1", RedisClient.EXPIRE_1DAY, true);
    }

    // 可用后删除
    public static boolean requestUuidAvailability(String uuid){
        String keyName = RedisClient.getKeyNameContainDateSuffix(REQUEST_UUID);
        boolean able = RedisClient.hexists(keyName, uuid);
        if(able){
            // 用后即焚
            RedisClient.hdel(keyName, uuid);
        }
        return able;
    }

}
