package com.fjsmu.app;

import com.fjsmu.comm.util.RestMsgUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * 异常处理
 *
 * @author a
 */
@Component
@Provider
public class ApplicationExceptionHandler implements ExceptionMapper<Exception> {

    private static Logger logger = Logger.getLogger(ApplicationExceptionHandler.class);

    @Override
    public Response toResponse(Exception e) {
        logger.error(e, e);
        if (e instanceof ForbiddenException) {
            return Response.status(Response.Status.FORBIDDEN).entity(ErrorUtils.FORBIDDEN).type(MediaType.APPLICATION_JSON_TYPE).build();
        } else if (e instanceof NotFoundException) {
            return Response.status(Response.Status.NOT_FOUND).entity(ErrorUtils.NOT_FOUND).type(MediaType.APPLICATION_JSON_TYPE).build();
        } else if(e instanceof NotAllowedException){
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(ErrorUtils.METHOD_NOT_ALLOWED).type(MediaType.APPLICATION_JSON_TYPE).build();
        } else if (e instanceof ErrorCodeException) {
            return Response.ok().entity(((ErrorCodeException) e).errorCode).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
        return RestMsgUtil.fail(e);
    }

}
