package com.fjsmu.modules.encounter.rest;

import com.fjsmu.app.ConstUtils;
import com.fjsmu.comm.util.RestMsgUtil;
import com.fjsmu.modules.encounter.service.EncounterService;
import org.apache.log4j.Logger;
import org.jboss.resteasy.spi.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
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
    @RolesAllowed("authenticated")
    public Response list(@QueryParam("status") int status, @Context HttpRequest req) throws Exception {
        return RestMsgUtil.successJackson(encounterService.listByStatus(status));
    }

    /**
     * 查看患者病例
     *
     * @param doctorId
     * @return
     * @throws Exception
     */
    @GET
    @Path("/doctor/{userId}")
    @Produces(ConstUtils.CONTENT_TYPE_UTF8)
    @RolesAllowed("authenticated")
    public Response doctorGet(@PathParam("userId") String doctorId) throws Exception {
        return RestMsgUtil.successJackson(encounterService.listByDoctorId(doctorId));
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
    @RolesAllowed("authenticated")
    public Response patientGet(@PathParam("userId") String userId) throws Exception {
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
    @Path("/patient/{userId}")
    @Produces(ConstUtils.CONTENT_TYPE_UTF8)
    @RolesAllowed("authenticated")
    public Response patientRegister(@PathParam("userId") String userId, String jsonData) throws Exception {
        return RestMsgUtil.successJackson(encounterService.patientRegister(userId, jsonData));
    }

    /**
     * 医生诊断病例
     *
     * @param encounterId
     * @param doctorId
     * @param jsonData
     * @return
     * @throws Exception
     */
    @PUT
    @Path("/{encounterId}/doctor/{doctorId}")
    @Produces(ConstUtils.CONTENT_TYPE_UTF8)
    @RolesAllowed("authenticated")
    public Response doctorUpdateEncounter(@PathParam("encounterId") String encounterId, @PathParam("doctorId") String doctorId, String jsonData) throws Exception {
        return RestMsgUtil.successJackson(encounterService.doctorUpdateEncounter(doctorId, encounterId, jsonData));
    }

}
