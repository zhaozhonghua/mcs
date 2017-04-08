package com.fjsmu.comm.util;

import com.fjsmu.util.HttpUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据Ip查询省市区
 *
 * Created by zzh on 17/1/25.
 */
public class IPLookupUtil {

    private static LoggerUtil logger = new LoggerUtil(org.apache.log4j.Logger.getLogger(IPLookupUtil.class));

    private static final String IP_URL = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip="; //新浪获取ip

    /**
     *
     * @param ip
     * @return {"ret":1,"start":-1,"end":-1,"country":"\u4e2d\u56fd","province":"\u5317\u4eac","city":"\u5317\u4eac",
     *          "district":"","isp":"","type":"","desc":""}
     * @return
     */
    public static Map<String, String> fetch(String ip) {
        try {
            Map<String, String> map = null;
            String response = HttpUtils.doGet(IP_URL+ip);
            logger.debug("IPLookupUtil fetch : " + response);
            JSONObject rec = new JSONObject(response);
            if(rec != null && rec.has("ret") && rec.getInt("ret") == 1){
                map = new HashMap<String, String>();
                if(rec.has("country")){
                    map.put("country", rec.getString("country"));
                }
                if(rec.has("province")){
                    map.put("province", rec.getString("province"));
                }
                if(rec.has("city")){
                    map.put("city", rec.getString("city"));
                }
                if(rec.has("district")){
                    map.put("district", rec.getString("district"));
                }
            }
            return map;
        }catch (Exception e){
            logger.warn(e.getMessage());
        }
        return null;
    }
}
