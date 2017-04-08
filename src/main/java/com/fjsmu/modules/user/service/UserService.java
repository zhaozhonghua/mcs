package com.fjsmu.modules.user.service;

import com.fjsmu.comm.util.RestMsgUtil;
import com.fjsmu.modules.user.dao.UserDao;
import com.fjsmu.modules.user.entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统用户逻辑处理
 * <p>
 * Created by zzh on 16/11/25.
 */
@Service
@Transactional
public class UserService {

    private Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;


    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return
     * @throws Exception
     */
    public User getUserById(String userId) throws Exception {
        return userDao.get(userId);
    }

    /**
     * 根据登陆名称获取用户信息
     *
     * @param loginName 登陆名称
     * @return
     * @throws Exception
     */
    public User getUserByLoginName(String loginName) throws Exception {
        return userDao.findByLoginName(loginName);
    }

    public User findByLoginNameIgnoreDelFlag(String loginName) throws Exception {
        return userDao.findByLoginNameIgnoreDelFlag(loginName);
    }

    /**
     * 添加用户信息
     *
     * @param userJson {""}
     * @return
     * @throws Exception
     */
    public User save(String userJson) throws Exception {
        User user = RestMsgUtil.convertJsonToEntity(userJson, User.class);
        userDao.save(user);
        return user;
    }

    /**
     * 根据type查询用户列表
     *
     * @param type
     * @return
     * @throws Exception
     */
    public List<User> list(int type) throws Exception{
        return userDao.list(type);
    }

}
