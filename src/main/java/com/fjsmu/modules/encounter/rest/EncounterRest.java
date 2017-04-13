package com.fjsmu.modules.encounter.rest;

import com.fjsmu.app.ConstUtils;
import com.fjsmu.comm.util.RestMsgUtil;
import com.fjsmu.modules.encounter.service.EncounterService;
import org.apache.log4j.Logger;
import org.jboss.resteasy.spi.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;


/**
 * 病例接口服务
 */
@Path("/encounter")
@Component
public class EncounterRest {

    private Logger logger = Logger.getLogger(EncounterRest.class);

    @Autowired
    private EncounterService encounterService;

    @GET
    @Produces(ConstUtils.CONTENT_TYPE_UTF8)
    public Response list(@Context HttpRequest req) throws Exception {
        return RestMsgUtil.successJackson(null);
    }

    /**
     * 查看患者病例
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @GET
    @Path("/patient/{userId}")
    @Produces(ConstUtils.CONTENT_TYPE_UTF8)
    public Response get(@PathParam("userId") String userId) throws Exception {
        return RestMsgUtil.successJackson(encounterService.listByUserId(userId));
    }

    /**
     * 挂号
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @POST
    @Path("/user/{userId}")
    @Produces(ConstUtils.CONTENT_TYPE_UTF8)
    public Response patientRegister(@PathParam("userId") String userId) throws Exception {
        return RestMsgUtil.successJackson(encounterService.patientRegister(userId));
    }

    @PUT
    @Path("/{menuId}")
    @Produces(ConstUtils.CONTENT_TYPE_UTF8)
    public Response put(@PathParam("menuId") String menuId, String jsonData) throws Exception {
        return null;
    }

    @DELETE
    @Path("/{menuId}")
    @Produces(ConstUtils.CONTENT_TYPE_UTF8)
    public Response delete(@PathParam("menuId") String menuId) throws Exception {
        return null;
    }

}
