package com.fjsmu.util.gson;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

/**
 * 使得子类兼具JsonSerializer、JsonDeserializer两接口功能。
 * ClassName: GsonTypeAdapter

 * @author midhua
 * Created by zzh on 16/10/20.
 */
@Deprecated
public abstract class GsonTypeAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {}
