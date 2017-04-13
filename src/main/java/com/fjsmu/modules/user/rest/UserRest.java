package com.fjsmu.modules.user.rest;

import com.fjsmu.app.ConstUtils;
import com.fjsmu.comm.util.RestMsgUtil;
import com.fjsmu.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * 用户REST
 */
@Path("/user")
@Component
public class UserRest {

    @Autowired
    private UserService userService;

    /**
     * 直播app用户登陆
     *
     * @param jsonData {"username": "18610245972", "password":"md5加密"}
     * @param req
     * @return
     * @throws Exception
     */
    @PermitAll
    @POST
    @Path("/login")
    @Produces(ConstUtils.CONTENT_TYPE_UTF8)
    public Response login(String jsonData, @Context HttpServletRequest req) throws Exception{
        Map<String, Object> paramMap = RestMsgUtil.convertJsonToMap(jsonData);
        return RestMsgUtil.successJackson(userService.login(paramMap, req));
    }

    @GET
    @Produces(ConstUtils.CONTENT_TYPE_UTF8)
    public Response list(@QueryParam("type") int type) throws Exception{
        return RestMsgUtil.successJackson(userService.list(type));
    }

    @POST
    @Produces(ConstUtils.CONTENT_TYPE_UTF8)
    public Response post(String jsonData) throws Exception{
        return RestMsgUtil.successJackson(userService.save(jsonData));
    }

}
