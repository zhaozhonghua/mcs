package com.fjsmu.comm.service;


import com.fjsmu.tools.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Created by fangcm on 2017/2/27.
 */
public class SimpleMailService {
    private static Logger logger = LoggerFactory.getLogger(SimpleMailService.class);

    private JavaMailSenderImpl mailSender;

    private String mailTo;
    private String mailSubject;
    private String mailText;

    /**
     * 发送纯文本的用户修改通知邮件.
     */
    public void sendNotificationMail() {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(mailSender.getUsername());
        msg.setTo(mailTo);
        msg.setSubject(mailSubject);
        msg.setText(mailText);

        try {
            mailSender.send(msg);
            if (logger.isInfoEnabled()) {
                logger.info("纯文本邮件已发送至{}", StringUtils.join(msg.getTo(), ","));
            }
        } catch (Exception e) {
            logger.error("发送邮件失败", e);
        }
    }

    /**
     * Spring的MailSender.
     */
    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailText() {
        return mailText;
    }

    public void setMailText(String mailText) {
        this.mailText = mailText;
    }

}
