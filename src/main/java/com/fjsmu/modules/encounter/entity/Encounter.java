package com.fjsmu.modules.encounter.entity;

import com.fjsmu.comm.entity.DataEntity;
import com.fjsmu.modules.user.entity.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

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

    public static final int STATUS_WAIT = 0; // 等待处理
    public static final int STATUS_PROCESSING = 1;   // 处理中
    public static final int STATUS_OVER = 2;   // 处理结束

    public Encounter() {
        super();
    }

    public Encounter(String id) {
        this();
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
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
