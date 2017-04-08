package com.fjsmu.modules.user.dao;

import com.fjsmu.comm.dao.BaseDao;
import com.fjsmu.comm.dao.Parameter;
import com.fjsmu.modules.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户DAO接口
 *
 * @author midhua
 * @version 2016-10-23
 */
@Repository
public class UserDao extends BaseDao<User> {

    /**
     * 根据用户token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public User getUserByToken(String token) throws Exception{
        Parameter parameter = new Parameter(token, User.DEL_FLAG_NORMAL);
        return getByHql("from User where token = :p1 and delFlag=:p2", parameter);
    }

    /**
     * 通过登录名查找用户
     * @param loginName 用户登录名
     * @return
     */
    public User findByLoginName(String loginName) throws Exception{
        Parameter parameter = new Parameter(loginName, User.DEL_FLAG_NORMAL);
        return getByHql("from User where (loginName = :p1 or mobile = :p1) and delFlag = :p2", parameter);
    }


    /**
     * 通过登录名查找用户
     * @param loginName 用户登录名
     * @return
     */
    public User findByLoginNameIgnoreDelFlag(String loginName) throws Exception{
        Parameter parameter = new Parameter(loginName);
        return getByHql("from User where (loginName = :p1 or mobile = :p1)", parameter);
    }

    /**
     * 通过登录名密码查找用户
     * @param loginName 用户登录名
     * @param password 密码
     *
     * @return
     */
    public User findByPassword(String loginName, String password) throws Exception{
        Parameter parameter = new Parameter(loginName, password);
        return getByHql("from User where (loginName = :p1 or mobile = :p1) and password = :p2 ", parameter);
    }

    /**
     * 根据type查询用户列表
     *
     * @return
     * @throws Exception
     */
    public List<User> list(int type) throws Exception{
        Parameter parameter = new Parameter(type, User.DEL_FLAG_NORMAL);
        return find("from User where type =:p1 and delFlag = :p2", parameter);
    }

}
