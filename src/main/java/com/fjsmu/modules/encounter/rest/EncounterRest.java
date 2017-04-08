package com.fjsmu.modules.encounter.rest;

import com.fjsmu.app.ConstUtils;
import com.fjsmu.comm.util.RestMsgUtil;
import org.apache.log4j.Logger;
import org.jboss.resteasy.spi.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;


/**
 * 病例接口服务
 */
@Path("/encounter")
@Component
public class EncounterRest {

    private Logger logger = Logger.getLogger(EncounterRest.class);

    @Autowired
    public Validator validator;


    @GET
    @Produces(ConstUtils.CONTENT_TYPE_UTF8)
    public Response list(@Context HttpRequest req) throws Exception {
        return RestMsgUtil.successJackson(null);
    }

    @GET
    @Path("/{encounterId}")
    @Produces(ConstUtils.CONTENT_TYPE_UTF8)
    public Response get(@PathParam("encounterId") String menuId, @Context Request req) throws Exception {
        return null;
    }

    @POST
    @Produces(ConstUtils.CONTENT_TYPE_UTF8)
    public Response post(String jsonData) throws Exception {
        return null;
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
