package com.fjsmu.modules.encounter.entity;

import com.fjsmu.comm.entity.DataEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import weixin.popular.bean.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 病例Entity
 *
 * @author tgs
 * @version 2017-3-20
 */
@Entity
@Table(name = "encounter")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Encounter extends DataEntity<Encounter> {

    private static final long serialVersionUID = 1L;

    private User patient;           //患者ID
    private String registerCode;    // 挂号编号
    private String description;     // 病例描述
    private int status;             // 状态 0：待处理 1 正在处理 2：处理完成

    public Encounter() {
        super();
    }

    public Encounter(String id) {
        this();
        this.id = id;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    @Length(min=1, max=64)
    @Column(name="register_code")
    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    @Length(min=1, max=255)
    @Column(name="description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
