package com.fjsmu.tools;


import java.beans.PropertyDescriptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.json.JSONArray;
import org.json.JSONObject;

@Deprecated
public class JsonUtils {

    public static JSONObject obj2Json(Object obj){
        
        return new JSONObject(obj2Map(obj));
    }
    
    public static String obj2JsonStr(Object obj){
        
        return new JSONObject(obj).toString();
    }
    
    public static JSONObject string2Json(String str){
        
        return new JSONObject(str);
    }
    
    public static JSONArray string2JsonArray(String str) {
        return new JSONArray(str);
    }
    
    public static Map<String, Object> jsonObject2Map(JSONObject jsonObject) {
        @SuppressWarnings("unchecked")
        Set<String> keySet = jsonObject.keySet();
        Map<String, Object> map = new HashMap<String, Object>();
        for(String key : keySet) {
            map.put(key, jsonObject.get(key));
        }
        return map;
    }
    
    public static Map<String, Object> obj2Map(Object obj){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if(obj != null) {
                PropertyDescriptor[] ps = PropertyUtils.getPropertyDescriptors(obj);
                String key = null;
                Object value = null;
                for(PropertyDescriptor p : ps){
                    key = p.getName();
                    if("class".equals(key) 
                            || "new".equals(key) 
                            || "persistentState".equals(key) 
                            || "pkName".equals(key)  
                            || "pk".equals(key)) 
                        continue;
                    value= PropertyUtils.getProperty(obj, key);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    public static void main(String args[]){
        String message="{\"datatype\":\"0\",\"client_id\":\"1A3135239C2F484393F1CF2C6B2D5B4C\",\"file_id\":\"\",\"file_path\":\"C:/Users/csc/Desktop/�����ĵ�/sasadf - ����3fgdsgsdfgdoc\",\"file_name\":\"sasadf - ����2.doc\",\"sensitivity\":\"2\",\"file_summary\":\"���� ����\",\"file_keywords\":\"����\",\"find_time\":\"2015-02-14 17:29\",\"is_redheadfile\":\"0\",\"file_size\":\"10240\"}";        
        JSONObject jsonInfo = JsonUtils.string2Json(message);
        System.out.println(jsonInfo.get("file_path"));
    }
    
}
