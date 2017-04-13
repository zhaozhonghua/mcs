package com.fjsmu.modules.user.service;

import com.fjsmu.comm.util.HttpParamUtil;
import com.fjsmu.comm.util.RestMsgUtil;
import com.fjsmu.modules.user.dao.UserDao;
import com.fjsmu.modules.user.entity.User;
import com.fjsmu.tools.IdGen;
import com.fjsmu.tools.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    /**
     * 直播app老师登陆
     *
     * @param parameterMap {"username": "18610245972", "password":"xxxxxx"}
     * @return
     * @throws Exception
     */
    public User login(Map<String, Object> parameterMap, HttpServletRequest request) throws Exception {

        String loginIp = HttpParamUtil.getIpAddr(request);

        String username = (String) parameterMap.getOrDefault("username", "");
        String password = (String) parameterMap.getOrDefault("password", "");

        logger.info("username : " + username + ", loginIp : " + loginIp);


        if (StringUtils.isEmpty(username)) {
            throw new Exception("用户名不能为空");
        }

        if (StringUtils.isEmpty(password)) {
            throw new Exception("密码不能为空");
        }

        User user = userDao.findByPassword(username, password);
        // 用户不存在, 就用手机号注册一个家长账号
        if (user == null || User.DEL_FLAG_DELETE.equals(user.getDelFlag())) {
            throw new Exception("用户不存在");

        }
        if (User.DEL_FLAG_DISABLE.equals(user.getDelFlag())) {
            throw new Exception("登录用户已被禁用，请联系管理员");
        }
        if (User.DEL_FLAG_AUDIT.equals(user.getDelFlag())) {
            throw new Exception("登录用户正在审核中，请联系管理员");
        }
        if (!User.DEL_FLAG_NORMAL.equals(user.getDelFlag())) {
            throw new Exception("用户不存在");
        }

        // 设置最后登陆ip和时间
        user.setLoginIp(loginIp);
        user.setLoginDate(new Date());

        // 每次登陆重新生成token
        if(StringUtils.isEmpty(user.getToken())){
            user.setToken(IdGen.uuid());
        }

        return user;
    }
}
