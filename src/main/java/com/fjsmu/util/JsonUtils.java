package com.fjsmu.util;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.Set;

/**
 * JsonUtils
 * @author midhua
 * Created by zzh on 16/10/23.
 */
public class JsonUtils {

	private static Logger logger = Logger.getLogger(JsonUtils.class);

	/**
	 * 判断jsonstring 中 key是否存在
	 *
	 * @param jsonString json串
	 * @param key 键
	 * @return
	 */
	public static boolean exist(String jsonString, String key){
		try{
			JSONObject jsonObject = new JSONObject(jsonString);
			Set<String> jsonKeys = jsonObject.keySet();
			if (jsonKeys != null){
				return jsonKeys.contains(key);
			}
		}catch (Exception e){
			logger.error(e);
		}
		return false;
	}
}
