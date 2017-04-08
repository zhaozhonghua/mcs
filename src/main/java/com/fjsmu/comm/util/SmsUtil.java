package com.fjsmu.comm.util;

import cn.jpush.api.push.model.SMS;
import com.fjsmu.cache.SmsCache;
import com.fjsmu.tools.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 短信util
 *
 * Created by zzh on 16/11/29.
 */
public class SmsUtil {

    private static Logger logger = Logger.getLogger(SmsUtil.class);

    public static final String LOGIN_VERIFY_CODE_MSG = "您的验证码是%s，请于5分钟内正确输入。";

    private static final String HOST = "sapi.253.com";

    private static final String ACCOUNT = "yyt668";

    private static final String PSWD = "qasxQASX1234";

    private static final String SMS_BASE_URL = "http://"+HOST+"/msg/HttpBatchSendSM?account="+ACCOUNT+"&pswd="+PSWD;

    // 必填参数。是否需要状态报告，取值true或false，true，表明需要状态报告；false不需要状态报告
    private static final String NEEDSTATUS = "false";

    private static final int EXTNO = 888;  // 可选参数，扩展码，用户定义扩展码，3位

    private static final int CONNECT_TIMEOUT = 30 * 1000; // 30s

    private static final int READ_TIMEOUT = 30 * 1000; // 30s

    /**
     * 发送短信信息
     *
     * @param mobile
     * @param msg
     * @return
     */
    public static boolean sendSms(String mobile, String msg) {

        logger.info("SMS: send verify sms to "+mobile);

        try {
            msg = URLEncoder.encode(msg, "utf-8");
            String url = String.format("%s&mobile=%s&msg=%s&needstatus=%s&extno=%d", SMS_BASE_URL, mobile, msg, NEEDSTATUS, EXTNO);
            logger.info("SMS: YunTongXunSms URL: "+url);

            Client client = new ResteasyClientBuilder().establishConnectionTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .socketTimeout(READ_TIMEOUT, TimeUnit.SECONDS).build();

            WebTarget target = client.target(url);
            Response resp = target.request().header("Content-type", "application/x-www-form-urlencoded").
                    header("Accept", "text/plain").get();

            if (resp.getStatus() == 200) {
                String value = resp.readEntity(String.class);
                resp.close();

                logger.info("SMS: YunTongXunSms res: "+ value);

                if("0".equals(value.split(",")[1])){
                    return true;
                }
            }
        } catch(Exception e) {
            logger.error(e);
        }

        return false;
    }

    /**
     * 生成短信验证码
     *
     * @param count
     * @return
     */
    public static String genVerifyCode(int count) {
        StringBuffer sb = new StringBuffer();
        String str = "0123456789";
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            int num = r.nextInt(str.length());
            sb.append(str.charAt(num));
            str = str.replace((str.charAt(num) + ""), "");
        }
        return sb.toString();
    }

    /**
     * 发送销售认证验证码
     *
     * @param smsJson
     *            {"mobile": "18610245972"}
     * @throws Exception
     */
    public static void sendVerifyCode(String smsJson, String key) throws Exception {
        Map<String, Object> parameterMap = RestMsgUtil.convertJsonToMap(smsJson);
        String mobile = (String) parameterMap.get("mobile");

        if (StringUtils.isEmpty(mobile)) {
            throw new Exception("手机号不能为空");
        }

        // TODO 手机号格式验证
        String verifyCode = SmsUtil.genVerifyCode(4);
        String sendMsg = String.format(SmsUtil.LOGIN_VERIFY_CODE_MSG, verifyCode);

        // TODO 一个手机号每天能发送次数的限制校验
        boolean isSuccess = SmsUtil.sendSms(mobile, sendMsg);
        if (!isSuccess) {
            throw new Exception("发送失败");
        }
        if(key.equals(SmsCache.SMS_LOGIN_VERIFY_CODE)){
            SmsCache.setLoginSmsVerifyCode(mobile, verifyCode);
        }else if(key.equals(SmsCache.SMS_SALES_AUTH_VERIFY_CODE)){
            SmsCache.setSalesAuthSmsVerifyCode(mobile, verifyCode);
        }
        SmsCache.setSmsMobileInc(mobile);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println(sendSms("13910120132", String.format(LOGIN_VERIFY_CODE_MSG, genVerifyCode(4))));
    }
}
