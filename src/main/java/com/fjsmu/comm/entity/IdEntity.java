package com.fjsmu.comm.entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import com.fjsmu.tools.IdGen;

/**
 * 数据Entity类
 * @author ThinkGem
 * @version 2013-05-28
 */
@MappedSuperclass
public abstract class IdEntity<T> extends BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String id;        // 编号

    public IdEntity() {
        super();
    }

    @PrePersist
    public void prePersist(){
        this.id = IdGen.uuid();
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
