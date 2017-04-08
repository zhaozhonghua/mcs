package com.fjsmu.comm.util;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by fangcm on 2017/2/6.
 */
public class HttpClientUtil {

    private static Builder build(String url) {
        Client client = ResteasyClientBuilder.newClient();
        return client.target(url).request(MediaType.APPLICATION_JSON_TYPE.withCharset("UTF-8"));
    }

    public static String doGet(String url) throws Exception {
        Response response = build(url).get();
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new Exception("HTTP Request is not success, Response code is " + response.getStatus());
        }
        String ret = response.readEntity(String.class);
        response.close();
        return ret;
    }

    public static String doPost(String url, Object data) throws Exception {
        Response response = build(url).post(Entity.json(data));
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            throw new Exception("HTTP Request is not success, Response code is " + response.getStatus());
        }
        String ret = response.readEntity(String.class);
        response.close();
        return ret;
    }


}
