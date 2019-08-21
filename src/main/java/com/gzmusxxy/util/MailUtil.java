package com.gzmusxxy.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailUtil{

    private static String host = "smtp.qq.com";
    private static String username = "930926134@qq.com";
    private static String password = "xjezwwkfbmgvbfgb";
    private static String defaultEncoding = "UTF-8";
    private static String port = "465";
    private static JavaMailSenderImpl javaMailSenderImpl;

    /**
     * 设置邮件信息
     * @return
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    private static JavaMailSenderImpl createMimeMessage(){
        javaMailSenderImpl = new JavaMailSenderImpl();
        //设置邮件配置
        javaMailSenderImpl.setHost(host);
        javaMailSenderImpl.setUsername(username);
        javaMailSenderImpl.setPassword(password);
        javaMailSenderImpl.setDefaultEncoding(defaultEncoding);
        Properties p = new Properties();
        p.setProperty("mail.smtp.auth", "true");//开启认证
        p.setProperty("mail.debug", "true");//启用调试
        p.setProperty("mail.smtp.timeout", "1000");//设置链接超时
        p.setProperty("mail.smtp.port", port);//设置端口
        p.setProperty("mail.smtp.socketFactory.port", port);//设置ssl端口
        p.setProperty("mail.smtp.socketFactory.fallback", "false");
        p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailSenderImpl.setJavaMailProperties(p);
        return javaMailSenderImpl;
    }

    /**
     * 发送邮件
     * subject:邮件主题
     * to:收件人账号
     * content:邮件内容
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    public static void sendMail(String subject,String content, String to) {
        try {
            //设置邮件内容
            JavaMailSenderImpl mailSender = createMimeMessage();
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true,"utf-8");
            messageHelper.setFrom(username);
            messageHelper.setSubject(subject);
            messageHelper.setText(content);
            messageHelper.setTo(to);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}