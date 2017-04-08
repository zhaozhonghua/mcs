package com.fjsmu.comm.entity;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Entity支持类
 *
 * @author ThinkGem
 * @version 2013-01-15
 */
@MappedSuperclass
public abstract class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    // 显示/隐藏
    public static final String SHOW = "1";
    public static final String HIDE = "0";
    
    // 是/否
    public static final String YES = "1";
    public static final String NO = "0";

    // 删除标记（0：正常；1：删除；2：审核；）
    public static final String FIELD_DEL_FLAG = "delFlag";
    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";
    public static final String DEL_FLAG_AUDIT = "2";
    public static final String DEL_FLAG_DISABLE = "9";

}
