package com.fjsmu.util.gson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * GsonDataFotmat
 *
 * @author midhua
 * Created by zzh on 16/10/20.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Deprecated
public @interface GsonDataFotmat {

    String dateFormat() default "yyyy-MM-dd HH:mm:ss";

    String stringFormat() default "";

}