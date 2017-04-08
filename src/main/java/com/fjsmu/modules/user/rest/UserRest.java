package com.fjsmu.modules.user.rest;

import com.fjsmu.app.ConstUtils;
import com.fjsmu.comm.util.RestMsgUtil;
import com.fjsmu.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * 用户REST
 */
@Path("/user")
@Component
public class UserRest {

    @Autowired
    private UserService userService;


//    @RolesAllowed("authenticated")
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
