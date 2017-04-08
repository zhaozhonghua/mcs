package com.fjsmu.cache;

import com.fjsmu.app.ConfigUtils;
import com.fjsmu.comm.util.LoggerUtil;
import com.fjsmu.tools.DateUtils;
import com.fjsmu.tools.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * Redis Cache
 * <p>
 * Created by zzh on 16/11/28.
 */
public class RedisClient {

    private static final JedisPool jedisPoll;
    public static int EXPIRE_1DAY = 60 * 60 * 24;
    private static LoggerUtil logger = new LoggerUtil(org.apache.log4j.Logger.getLogger(RedisClient.class));

    static {
        String host = ConfigUtils.getString("redis_host");
        if(StringUtils.isBlank(host)){
            host = "127.0.0.1";
        }
        int port = ConfigUtils.getInteger("redis_port");
        if(port <=0){
            port = 6379;
        }

        JedisPoolConfig jpc = new JedisPoolConfig();
        jpc.setMaxTotal(256);
        jedisPoll = new JedisPool(jpc, host, port);
    }

    public static Jedis getRedis() {
        Jedis jedis = null;
        try {
            jedis = jedisPoll.getResource();
            return jedis;
        } catch (Exception e) {
            if (jedis != null) {
                jedis.close();
            }
            return null;
        }
    }

    public static String set(String keyName, String value, int expire) {
        Jedis jedis = getRedis();
        String ret = null;
        try {
            if (jedis != null && StringUtils.isNotEmpty(keyName)) {
                if (expire > 0) {
                    ret = jedis.setex(keyName, expire, value);
                } else {
                    ret = jedis.set(keyName, value);
                }
                return ret;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return ret;
    }

    public static String get(String keyName) {
        Jedis jedis = getRedis();
        String ret = null;
        try {
            if (jedis != null && StringUtils.isNotEmpty(keyName)) {
                ret = jedis.get(keyName);
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return ret;
    }

    public static Long del(String keyName) {
        Jedis jedis = getRedis();
        Long ret = null;
        try {
            if (jedis != null && StringUtils.isNotEmpty(keyName)) {
                ret = jedis.del(keyName);
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return ret;
    }

    public static int hset(String keyName, final String fieldName, String value, int expire, boolean dateSuffix) {
        Jedis jedis = getRedis();
        try {
            int ret = 0;
            if (jedis != null && StringUtils.isNotEmpty(keyName) && StringUtils.isNotEmpty(fieldName)) {

                if (dateSuffix) {
                    keyName = getKeyNameContainDateSuffix(keyName);
                }

                if (jedis.exists(keyName)) {
                    ret = jedis.hset(keyName, fieldName, value).intValue();
                } else {
                    ret = jedis.hset(keyName, fieldName, value).intValue();
                    if (expire > 0) {
                        jedis.expire(keyName, expire);
                    }
                }
                return ret;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return -1;
    }

    public static boolean hexists(String keyName, final String fieldName) {
        Jedis jedis = getRedis();
        try {
            return jedis.hexists(keyName, fieldName).booleanValue();
        } catch (Exception e) {

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    public static int hdel(String keyName, final String fieldName) {
        Jedis jedis = getRedis();
        try {
            return jedis.hdel(keyName, fieldName).intValue();
        } catch (Exception e) {

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return -1;
    }

    public static String hgetStr(String keyName, final String fieldName) {
        Jedis jedis = getRedis();
        try {
            if (jedis != null && StringUtils.isNotEmpty(keyName) && StringUtils.isNotEmpty(fieldName)) {
                String val = jedis.hget(keyName, fieldName);
                if (val != null) {
                    return val;
                }
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public static int hgetInt(String keyName, final String fieldName) {
        Jedis jedis = getRedis();
        try {
            if (jedis != null && StringUtils.isNotEmpty(keyName) && StringUtils.isNotEmpty(fieldName)) {
                String val = jedis.hget(keyName, fieldName);
                if (val != null) {
                    return Integer.valueOf(val);
                }
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return -1;
    }

    public static int hinc(String keyName, final String fieldName, int expire, boolean dateSuffix) {
        Jedis jedis = getRedis();
        try {
            int ret = 0;
            if (jedis != null && keyName != null && fieldName != null) {

                if (dateSuffix) {
                    keyName = getKeyNameContainDateSuffix(keyName);
                }

                if (jedis.exists(keyName)) {
                    ret = jedis.hincrBy(keyName, fieldName, 1).intValue();
                } else {
                    ret = jedis.hincrBy(keyName, fieldName, 1).intValue();
                    if (expire > 0) {
                        jedis.expire(keyName, expire);
                    }
                }
                return ret;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return -1;
    }


    /**
     * 获取key包含时间后缀
     *
     * @param keyName
     * @return
     */
    public static String getKeyNameContainDateSuffix(String keyName) {
        return keyName + ":" + currDateSuffix();
    }

    /**
     * 获取key的时间后缀
     *
     * @return
     */
    private static String currDateSuffix() {
        return DateUtils.getDate("yyyyMMdd");
    }

    public static int zinc(String keyName, final String fieldName, int increment) {
        Jedis jedis = getRedis();
        try {
            int ret = 0;
            if (jedis != null && keyName != null && fieldName != null) {
                ret = jedis.zincrby(keyName, increment, fieldName).intValue();
                return ret;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public static int zinc(String keyName, final String fieldName) {
        return zinc(keyName, fieldName, 1);
    }

    public static int zdec(String keyName, final String fieldName) {
        return zinc(keyName, fieldName, -1);
    }

    public static int zgetInt(String keyName, final String fieldName) {
        Jedis jedis = getRedis();
        try {
            if (jedis != null && StringUtils.isNotEmpty(keyName) && StringUtils.isNotEmpty(fieldName)) {
                Double val = jedis.zscore(keyName, fieldName);
                if (val != null) {
                    return val.intValue();
                }
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public static Set<String> zrevrange(String keyName, long start, long stop) {
        Jedis jedis = getRedis();
        try {
            if (jedis != null && StringUtils.isNotEmpty(keyName)) {
                Set<String> val = jedis.zrevrange(keyName, start, stop);
                return val;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public static long zrem(String keyName, final String fieldName) {
        Jedis jedis = getRedis();
        try {
            if (jedis != null && StringUtils.isNotEmpty(keyName) && StringUtils.isNotEmpty(fieldName)) {
                Long val = jedis.zrem(keyName, fieldName);
                return val;
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }
}
