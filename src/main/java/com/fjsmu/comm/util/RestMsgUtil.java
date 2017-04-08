package com.fjsmu.comm.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.*;
import com.fjsmu.app.ErrorCode;
import com.fjsmu.app.ErrorUtils;
import com.fjsmu.comm.entity.Page;
import com.fjsmu.tools.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.spi.HttpRequest;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.*;

public class RestMsgUtil {

    private static Logger logger = Logger.getLogger(RestMsgUtil.class);

    private RestMsgUtil() {
    }

    public static Map<String, Object> wrappingPageData(Page<?> page) {
        return wrappingPageData(page, page.getList());
    }
    public static Map<String, Object> wrappingPageData(Page<?> page, List<?> data) {
        Map<String, Object> result = new HashMap<>();
        result.put("pageData", data == null ? new ArrayList<>() : data);
        result.put("pageNo", page.getPageNo());
        result.put("totalPage", page.getTotalPage());
        result.put("pageSize", page.getPageSize());
        result.put("totalCount", page.getCount());

        return result;
    }

    public static Map<String, Object> getSuccessMap(Object data){
        return getSuccessMap(data, null);
    }

    public static Map<String, Object> getSuccessMap(Object data, String successTip){
        Map<String, Object> result = new HashMap<>();
        result.put(ErrorCode.ERROR_CODE, 0);
        result.put(ErrorCode.ERROR_MSG, StringUtils.isNotEmpty(successTip)? successTip : "处理成功");
        result.put("data", data == null ? new HashMap<String, Object>() : data);
        return result;
    }

    public static Response successJackson(Object data) {
        return Response.ok().entity(getSuccessMap(data, null)).build();
    }

    public static Response successJackson(Object data, String successTip) {
        return Response.ok().entity(getSuccessMap(data, successTip)).build();
    }

    public static Response response(boolean success, String msg) {
        Map<String, Object> result = new HashMap<>();
        result.put(ErrorCode.ERROR_MSG, msg);
        if (success) {
            result.put(ErrorCode.ERROR_CODE, 0);
            return Response.ok().entity(result).build();
        } else {
            result.put(ErrorCode.ERROR_CODE, ErrorUtils.OPERATION_FAILED.errcode);
            return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
        }
    }

    public static Response response(int errorCode, String msg) {
        Map<String, Object> result = new HashMap<>();
        result.put(ErrorCode.ERROR_CODE, errorCode);
        result.put(ErrorCode.ERROR_MSG, msg);
        return Response.ok().entity(result).build();
    }


    public static Response response(ErrorCode errorCode) {
        Map<String, Object> result = new HashMap<>();
        result.put(ErrorCode.ERROR_CODE, errorCode.errcode);
        result.put(ErrorCode.ERROR_MSG, errorCode.errmsg);
        return Response.ok().entity(result).build();
    }

    public static Response fail(ErrorCode errorCode) {
        Map<String, Object> result = new HashMap<>();
        result.put(ErrorCode.ERROR_CODE, errorCode.errcode);
        result.put(ErrorCode.ERROR_MSG, errorCode.errmsg);
        return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    public static Response fail(Exception e) {
        Map<String, Object> result = new HashMap<>();
        result.put(ErrorCode.ERROR_CODE, ErrorUtils.OPERATION_FAILED.errcode);
        if (e instanceof ConstraintViolationException) {
            List<String> msgs = BeanValidators.extractMessage((ConstraintViolationException) e);
            String message = StringUtils.join(msgs, ",");
            result.put(ErrorCode.ERROR_MSG, message);
            logger.warn(message);
        } else {
            result.put(ErrorCode.ERROR_MSG, e.getMessage() != null?e.getMessage():"处理失败");
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> convertJsonToMap(String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> maps;
        try {
            maps = objectMapper.readValue(json, Map.class);
            return maps;
        } catch (JsonParseException e) {
            throw new Exception("您输入的参数错误");
        } catch (JsonMappingException e) {
            throw new Exception("您输入的参数错误");
        }
    }

    /**
     * json字符串转换为hibernateEntiy
     *
     * eg:
     *   String name = "{\"name\": \"name\"}";
     *   Device device = RestMsgUtil.convertJsonToEntity(name, Device.class);
     *   System.out.println(device.getName());
     * @param json
     * @param valueType
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T convertJsonToEntity(String json, Class<T> valueType) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(dateFormat);

        return objectMapper.readValue(json, valueType);
    }

    public static String convertEntityToJson(Object obj) throws Exception {
        String json = "{}";
        if(obj != null){
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            json = ow.writeValueAsString(obj);
        }
        return json;
    }

    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> newMap = new HashMap<String, String>();
        Map<String, String[]> properties = request.getParameterMap();
        Set<String> keys = properties.keySet();
        for (String key : keys) {
            Object obj = properties.get(key);
            if (obj instanceof String[]) {
                newMap.put(key, ((String[]) obj)[0]);
            } else {
                newMap.put(key, obj.toString());
            }
        }
        return newMap;
    }

    public static Map<String, String> getParameterMap(HttpRequest request) {
        Map<String, String> newMap = new HashMap<>();
        Map<String, List<String>> properties = request.getUri().getQueryParameters();
        Set<String> keys = properties.keySet();
        for (String key : keys) {
            List<String> obj = properties.get(key);
            if (obj != null && obj.size() > 0) {
                newMap.put(key, obj.get(0));
            }
        }
        return newMap;
    }

    public static Response successJackson(int code, Object data) {
        JSONObject result = new JSONObject();
        result.put(ErrorCode.ERROR_CODE, code);
        result.put(ErrorCode.ERROR_MSG, "处理成功");
        result.put("data", data==null?new JSONObject():data);
        return Response.ok().entity(result.toString()).build();
    }

    public static Response successJsonContent(Object data) {
        JSONObject result = new JSONObject();
        result.put(ErrorCode.ERROR_CODE, 0);
        result.put(ErrorCode.ERROR_MSG, "处理成功");
        result.put("data", data==null?new JSONObject():data);
        return Response.ok().entity(result.toString()).build();
    }

    public static String getOkJsonString(String jsonData) {
        return "{\"" + ErrorCode.ERROR_CODE + "\":0, \"" + ErrorCode.ERROR_MSG + "\":\"成功\", \"data\":" + jsonData + "}";
    }

    public static Response successStringContent(String data) {
        return Response.status(Response.Status.OK).entity(getOkJsonString(data)).build();
    }

}
