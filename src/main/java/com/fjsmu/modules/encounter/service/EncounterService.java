package com.fjsmu.modules.encounter.service;

import com.fjsmu.app.ErrorCodeException;
import com.fjsmu.app.ErrorUtils;
import com.fjsmu.comm.util.RestMsgUtil;
import com.fjsmu.comm.util.SmsUtil;
import com.fjsmu.modules.encounter.dao.EncounterDao;
import com.fjsmu.modules.encounter.entity.Encounter;
import com.fjsmu.modules.user.dao.UserDao;
import com.fjsmu.modules.user.entity.User;
import com.fjsmu.tools.StringUtils;
import com.fjsmu.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 病例管理
 */
@Service
@Transactional
public class EncounterService {

    @Autowired
    private EncounterDao encounterDao;

    @Autowired
    private UserDao userDao;

    public List<Encounter> listByStatus(int status) throws Exception{
        return encounterDao.listByStatus(status);
    }

    /**
     * 根据患者用户ID查询病例列表
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public List<Encounter> listByUserId(String userId) throws Exception{
        return encounterDao.listByUserId(userId);
    }

    /**
     * 根据医生用户ID查询病例列表
     *
     * @param doctorId
     * @return
     * @throws Exception
     */
    public List<Encounter> listByDoctorId(String doctorId) throws Exception{
        return encounterDao.listByDoctorId(doctorId);
    }

    /**
     * 患者挂号
     *
     * @return
     */
    public Encounter patientRegister(String userId, String jsonData) throws Exception{

        Map<String, Object> paramMap = RestMsgUtil.convertJsonToMap(jsonData);

        String remark = paramMap.getOrDefault("remark", "").toString(); // 病例详述

        Encounter encounter = encounterDao.getEncounterByUserId(userId);
        if(encounter != null){
            throw new ErrorCodeException(ErrorUtils.PATIENT_ALREADY_REGISTER);
        }

        User patient = userDao.get(userId);

        encounter = new Encounter();
        encounter.setPatient(patient);
        encounter.setCreateBy(patient);
        encounter.setStatus(Encounter.STATUS_WAIT);
        encounter.setRegisterCode(SmsUtil.genVerifyCode(6));
        encounter.setRemarks(remark);

        encounterDao.save(encounter);

        return encounter;
    }

    /**
     * 医生更新病例
     *
     * @return
     * @throws Exception
     */
    public Encounter doctorUpdateEncounter(String doctorId, String encounterId, String jsonData) throws Exception{
        boolean updateFlag = false;
        Encounter encounter = encounterDao.get(encounterId);
        Encounter encounterUpdate = RestMsgUtil.convertJsonToEntity(jsonData, Encounter.class);
        User doctor = userDao.get(doctorId);

        if(doctor == null){
            throw new ErrorCodeException(ErrorUtils.DATA_NOT_EXIST);
        }

        if(encounter == null){
            throw new ErrorCodeException(ErrorUtils.DATA_NOT_EXIST);
        }

        if(encounterUpdate == null){
            throw new ErrorCodeException(ErrorUtils.REQUEST_PARAMETER_ERROR);
        }

        if(JsonUtils.exist(jsonData, "description")){
            encounter.setDescription(encounterUpdate.getDescription());
            updateFlag = true;
        }

        if (updateFlag){
            encounter.setStatus(Encounter.STATUS_OVER);
            encounter.setUpdateBy(doctor);
            encounter.setUpdateDate(new Date());
            encounterDao.save(encounter);
        }

        return encounter;
    }

}
