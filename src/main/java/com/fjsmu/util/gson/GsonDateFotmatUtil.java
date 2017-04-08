package com.fjsmu.util.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.fjsmu.tools.StringUtils;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * GsonDateFotmatUtil
 *
 * @author midhua
 * Created by zzh on 16/10/20.
 */
@Deprecated
public class GsonDateFotmatUtil {

    public static <T> Map<Class<?>, Object> processSerializable(Class<T> clazz) {
        if(clazz==null)
            return null;
        if (!clazz.isAnnotationPresent(GsonDataFotmat.class))
            return null;
        Map<Class<?>, Object> adapterMap = new HashMap<Class<?>, Object>();
        GsonDataFotmat format = clazz.getAnnotation(GsonDataFotmat.class);
        if(format!=null&&!StringUtils.isEmpty(format.dateFormat()))
            addDateFormat(format.dateFormat(),adapterMap);
        if(format!=null&&!StringUtils.isEmpty(format.stringFormat()))
            addStringFormat(format.dateFormat(),adapterMap);
        return adapterMap;
    }

    private static <T> void addStringFormat(String pattern,Map<Class<?>, Object> adapterMap){
        //TODO
    }

    private static void addDateFormat(String pattern,Map<Class<?>, Object> adapterMap){
        final SimpleDateFormat df=new SimpleDateFormat(pattern);
        GsonTypeAdapter<Date> dateFormater =  new GsonTypeAdapter<Date>(){
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                String result = "";
                if(src!=null){

                    result = df.format(src);
                }
                return new JsonPrimitive(result);
            }

            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
                if(json == null)
                    return null;
                else{
                    try {
                        return df.parse(json.getAsString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return null;

            }
        };
        adapterMap.put(Date.class,dateFormater);
    }
}
