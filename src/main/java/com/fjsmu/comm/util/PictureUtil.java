package com.fjsmu.comm.util;

import com.fjsmu.app.ConfigUtils;
import com.fjsmu.tools.StringUtils;

/**
 * 图片处理 util
 *
 * Created by zzh on 17/1/23.
 */
public class PictureUtil {


    /**
     * 兼容图片带http头的图片
     *
     * @param url
     * @return
     */
    public static String removePictureDomain(String url){
        return removeDomain(url, 0);
    }

    /**
     * 兼容图片带http头的视频
     *
     * @param url
     * @return
     */
    public static String removeVideoDomain(String url){
        return removeDomain(url, 1);
    }

    /**
     * 如果用户传的图片地址带http， 替换掉
     *
     * @param url
     * @return
     */
    private static String removeDomain(String url, int type){
        if(StringUtils.isNotEmpty(url) && url.startsWith("http")){
            String host = "";
            if(type == 0){
                host = ConfigUtils.getString("imgUrl");
            }else{
                host = ConfigUtils.getString("videoUrl");
            }
            return url.replace(host, "");
        }
        return url;
    }
}
