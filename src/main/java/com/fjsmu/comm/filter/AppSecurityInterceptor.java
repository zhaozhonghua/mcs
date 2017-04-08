package com.fjsmu.comm.filter;

import com.fjsmu.tools.StringUtils;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Provider
@Component
//@Priority(Priorities.AUTHENTICATION)
public class AppSecurityInterceptor implements ContainerRequestFilter {

    private Logger logggr = LoggerFactory.getLogger(AppSecurityInterceptor.class);

    private static final String BASE_API_ADMIN = "/dz/admin/";
    private static final String BASE_API_APP = "/dz/app/";
    private static final String BASE_API_MOBILE = "/dz/mobile/";

    private static final String STUDENT_PROPERTY = "StudentId";
    private static final String DEVICE_PROPERTY = "DeviceUuid";
    private static final String SCAN_LOGIN_PROPERTY = "ScanLogin";

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Token";
    private static final ServerResponse ACCESS_DENIED = new ServerResponse("{\"errcode\": 40001, \"errmsg\": \"Access denied for this resource\"}", 401, new Headers<Object>());
    private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("{\"errcode\": 40003, \"errmsg\": \"Nobody can access this resource\"}", 403, new Headers<Object>());
    private static final ServerResponse SERVER_ERROR = new ServerResponse("{\"errcode\": 40003, \"errmsg\": \"INTERNAL SERVER ERROR\"}", 500, new Headers<Object>());

    @Context
    private HttpServletRequest httpServletRequest;

    @Context
    private HttpServletResponse httpServletResponse;


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");

        String reqAbsolutePath = requestContext.getUriInfo().getAbsolutePath().getPath();

        Method method = methodInvoker.getMethod();

        // mobile 和 app 端
        if (reqAbsolutePath.startsWith(BASE_API_MOBILE)
                || reqAbsolutePath.startsWith(BASE_API_APP)){

            // Get request headers
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();

            // Fetch authorization header
            final String authorization = headers.getFirst(AUTHORIZATION_PROPERTY);

            String studentId = headers.getFirst(STUDENT_PROPERTY);
            String deviceUuid = headers.getFirst(DEVICE_PROPERTY);

            // Access allowed for all
            if (method.isAnnotationPresent(PermitAll.class)) {
                return;
            }
            // Access denied for all
            if (method.isAnnotationPresent(DenyAll.class)) {
                requestContext.abortWith(ACCESS_FORBIDDEN);
                return;
            }

            // If no authorization information present; block access
//            if (authorization == null
//                    || authorization.isEmpty()
//                    || !authorization.startsWith(AUTHENTICATION_SCHEME + " ")) {
            if (authorization == null
                    || authorization.isEmpty()) {
                requestContext.abortWith(ACCESS_DENIED);
                return;
            }

            // Get encoded username and password
            final String userToken = authorization.replaceFirst(AUTHENTICATION_SCHEME + " ", "");

            logggr.info(LogRecord.app(requestContext, httpServletRequest, httpServletResponse, userToken, deviceUuid, studentId));

            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
                if(rolesSet.contains("authenticated") && StringUtils.isNotEmpty(userToken)){
                    try{

                    }catch (Exception e){
                        requestContext.abortWith(ACCESS_FORBIDDEN);
                        return;
                    }
                }
            }
        }
    }
}
