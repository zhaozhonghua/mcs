package com.fjsmu.comm.filter;

import com.fjsmu.comm.util.HttpParamUtil;
import com.fjsmu.comm.util.SessionUtil;
import com.fjsmu.tools.DateUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 请求日志记录
 *
 * Created by zzh on 16/10/19.
 */

public class LogRecord {

    private static Logger logggr = LoggerFactory.getLogger(LogRecord.class);

    private static List<String> paramEscapeList() {
        List<String> escapeList = new ArrayList<String>();
        escapeList.add("password");
        escapeList.add("newpassword");
        return escapeList;
    }

    private static JSONObject getRequestBody(ContainerRequestContext requestContext) {

        try {

            ByteArrayInputStream requestBody = getInputStream(requestContext);

            if(requestBody == null){
                logggr.debug("requestBody is null");
                return null;
            }

            StringWriter writer = new StringWriter();
            IOUtils.copy(requestBody, writer);
            String jsonData = writer.toString();
            if (!"".equals(jsonData)) {

//                // 微信回复的xml过滤
//                if(jsonData.startsWith("<xml>")){
//                    requestBody.reset();
//                    requestContext.setEntityStream(requestBody);
//                    return new JSONObject();
//                }

                JSONObject body = new JSONObject(jsonData);
                for (String key : body.keySet()) {
                    if (paramEscapeList().contains(key)) {
                        body.putOpt(key, "******");
                    }
                }
                requestBody.reset();
                requestContext.setEntityStream(requestBody);

                return body;
            }
        } catch (Exception e) {
            logggr.error(e.getMessage());
        }
        return null;
    }

    private static ByteArrayInputStream getInputStream(ContainerRequestContext requestContext){
        try{
            InputStream entityStream = requestContext.getEntityStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = entityStream.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return new ByteArrayInputStream(baos.toByteArray());
        }catch (IOException e){
            return null;
        }
    }

    private static JSONObject getParams(ContainerRequestContext requestContext){

        JSONObject jsonObject = getRequestBody(requestContext);
        if (jsonObject == null){
            jsonObject = new JSONObject();
        }

        MultivaluedMap<String, String> queryParameters = requestContext.getUriInfo().getQueryParameters();
        Iterator<String> it = queryParameters.keySet().iterator();
        while (it.hasNext()) {
            String theKey = (String) it.next();
            if (paramEscapeList().contains(theKey)) {
                jsonObject.putOpt(theKey, "******");
                continue;
            }
            List<String> paramList = queryParameters.get(theKey);
            if (paramList.size() > 1) {
                jsonObject.putOpt(theKey, "[" + String.join(",", paramList) + "]");
            } else {
                jsonObject.putOpt(theKey, queryParameters.getFirst(theKey));
            }
        }
        return jsonObject;
    }

    protected static String app(ContainerRequestContext requestContext,
                                   HttpServletRequest httpServletRequest,
                                   HttpServletResponse httpServletResponse,
                                   String token, String deviceUUID, String studentId){

        SessionUtil.setSessionId(getSessionId(httpServletRequest));

        String reqAbsolutePath = requestContext.getUriInfo().getAbsolutePath().getPath();
        JSONObject jsonObject = new JSONObject();
        JSONObject appData = new JSONObject();
        jsonObject.put("remote_ip", HttpParamUtil.getIpAddr(httpServletRequest));
        jsonObject.put("req_time", DateUtils.getDate("yyyy-MM-dd HH:mm:ss.SSS"));
        jsonObject.put("req_uri", reqAbsolutePath);
        jsonObject.put("req_method", requestContext.getMethod());
        jsonObject.put("user_agent", httpServletRequest.getHeader("User-Agent"));
        jsonObject.put("req_params", getParams(requestContext));
        jsonObject.put("status_code", httpServletResponse.getStatus());
        jsonObject.put("referer", httpServletRequest.getHeader("Referer"));

        jsonObject.put("appid", 1); // 应用ID

        appData.put("token", token);
        appData.put("device_uuid", deviceUUID);
        appData.put("student_id", studentId);
        appData.put("session_id", SessionUtil.getSessionId());
        jsonObject.put("app_data", appData); // 应用ID

        return jsonObject.toString();
    }

    protected static String admin(ContainerRequestContext requestContext,
                                HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse, String token){
        String reqAbsolutePath = requestContext.getUriInfo().getAbsolutePath().getPath();
        JSONObject jsonObject = new JSONObject();
        JSONObject appData = new JSONObject();

        jsonObject.put("remote_ip", HttpParamUtil.getIpAddr(httpServletRequest));
        jsonObject.put("req_time", DateUtils.getDate("yyyy-MM-dd HH:mm:ss.SSS"));
        jsonObject.put("req_uri", reqAbsolutePath);
        jsonObject.put("req_method", requestContext.getMethod());
        jsonObject.put("user_agent", httpServletRequest.getHeader("User-Agent"));
        jsonObject.put("req_params", getParams(requestContext));
        jsonObject.put("status_code", httpServletResponse.getStatus());
        jsonObject.put("referer", httpServletRequest.getHeader("Referer"));

        appData.put("token", token);
        appData.put("session_id", getSessionId(httpServletRequest));
        jsonObject.put("appid", 2);
        jsonObject.put("app_data", appData); // 应用ID

        return jsonObject.toString();
    }

    protected static String live(ContainerRequestContext requestContext,
                                  HttpServletRequest httpServletRequest,
                                  HttpServletResponse httpServletResponse, String token){
        String reqAbsolutePath = requestContext.getUriInfo().getAbsolutePath().getPath();
        JSONObject jsonObject = new JSONObject();
        JSONObject appData = new JSONObject();

        jsonObject.put("remote_ip", HttpParamUtil.getIpAddr(httpServletRequest));
        jsonObject.put("req_time", DateUtils.getDate("yyyy-MM-dd HH:mm:ss.SSS"));
        jsonObject.put("req_uri", reqAbsolutePath);
        jsonObject.put("req_method", requestContext.getMethod());
        jsonObject.put("user_agent", httpServletRequest.getHeader("User-Agent"));
        jsonObject.put("req_params", getParams(requestContext));
        jsonObject.put("status_code", httpServletResponse.getStatus());
        jsonObject.put("referer", httpServletRequest.getHeader("Referer"));

        jsonObject.put("appid", 3);

        appData.put("token", token);
        appData.put("session_id", getSessionId(httpServletRequest));
        jsonObject.put("app_data", appData); // 应用ID
        return jsonObject.toString();
    }

    private static String getSessionId(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        if(session != null){
            return session.getId();
        }
        return "";
    }

}
