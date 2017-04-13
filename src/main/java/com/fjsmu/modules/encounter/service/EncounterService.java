package com.fjsmu.modules.encounter.service;

import com.fjsmu.app.ErrorCodeException;
import com.fjsmu.app.ErrorUtils;
import com.fjsmu.comm.util.SmsUtil;
import com.fjsmu.modules.encounter.dao.EncounterDao;
import com.fjsmu.modules.encounter.entity.Encounter;
import com.fjsmu.modules.user.dao.UserDao;
import com.fjsmu.modules.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     * 患者挂号
     *
     * @return
     */
    public Encounter patientRegister(String userId) throws Exception{

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

        encounterDao.save(encounter);

        return encounter;
    }

}
