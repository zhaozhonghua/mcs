package com.fjsmu.util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

/**
 * GsonHelper
 *
 * @author midhua
 * Created by zzh on 16/10/20.
 */
@Deprecated
public class GsonHelper {

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return fromJson(json, classOfT, GsonDateFotmatUtil.processSerializable(classOfT));
    }

    public static <T> T fromJson(String json, Class<T> classOfT, Map<Class<?>, Object> adapters) {

        Gson gson = getGson(adapters);
        return gson.fromJson(json, classOfT);
    }
    public static String toJson(Object jsonElement) {
        return toJson(jsonElement, GsonDateFotmatUtil.processSerializable(jsonElement.getClass()));
    }

    public static String toJson(Object jsonElement, Map<Class<?>, Object> adapters) {

        Gson gson = getGson(adapters);
        return gson.toJson(jsonElement);
    }

    public static Gson getGson(Map<Class<?>, Object> adapters) {
        Gson gson = null;
        if (adapters != null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            for (Map.Entry<Class<?>, Object> entry : adapters.entrySet()) {
                gsonBuilder.registerTypeAdapter(entry.getKey(), entry.getValue());
            }
            gson = gsonBuilder.create();
        } else {
            gson = new Gson();
        }
        return gson;
    }
}
