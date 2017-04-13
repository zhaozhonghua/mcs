package com.fjsmu.modules.encounter.dao;

import com.fjsmu.comm.dao.BaseDao;
import com.fjsmu.comm.dao.Parameter;
import com.fjsmu.modules.encounter.entity.Encounter;
import com.fjsmu.modules.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EncounterDao extends BaseDao<Encounter> {

    /**
     * 根据患者ID查询病例信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public Encounter getEncounterByUserId(String userId) throws Exception{
        Parameter parameter = new Parameter(userId, User.DEL_FLAG_NORMAL, Encounter.STATUS_OVER);
        return getByHql("from Encounter where patient.id = :p1 and delFlag=:p2 and status!=:p3", parameter);
    }

    /**
     * 根据患者ID查询病例列表
     *
     * @return
     * @throws Exception
     */
    public List<Encounter> listByUserId(String userId) throws Exception{
        Parameter parameter = new Parameter(userId, User.DEL_FLAG_NORMAL);
        return find("from Encounter where patient.id = :p1 and delFlag = :p2", parameter);
    }

    /**
     * 根据type查询用户列表
     *
     * @return
     * @throws Exception
     */
    public List<Encounter> list() throws Exception{
        Parameter parameter = new Parameter(User.DEL_FLAG_NORMAL);
        return find("from Encounter where delFlag = :p1", parameter);
    }

    /**
     * 根据type查询用户列表
     *
     * @return
     * @throws Exception
     */
    public List<Encounter> listByStatus(int status) throws Exception{
        Parameter parameter = new Parameter(status, User.DEL_FLAG_NORMAL);
        return find("from Encounter where status = :p1 and delFlag = :p2", parameter);
    }

}
