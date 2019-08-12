package com.gzmusxxy.util;

import com.gzmusxxy.entity.WeChat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用于发送模板消息的类
 */
public class WeChatUtil {
    //实例化RestTemplate
    private static RestTemplate restTemplate = new RestTemplate();

    /**
     * 封装请求类发送请求
     * 供其他方法调用
     *
     * @param param
     * @return
     */
    private static WeChat sendTemplatMsg(String param) {
        //设置Http的头部信息
        HttpHeaders headers = new HttpHeaders();
        //设置编码
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<>(param, headers);
        return restTemplate.postForObject(WxConfig.getSendWechatMsgUrl(), formEntity, WeChat.class);
    }

    /**
     * 发送业务通知信息
     *
     * Usage:
     * WeChatUtil.sendBusinessNoticeMsg("oP1se1IxKwG6Su0y6K4Q-_3pkN_Y", "RLDS你好！！", "信息提交", "你的审核信息通过", "如有问题请联系我们！联系电话xxxxxxxxxxx","");
     * Template:
     * --------------------------
     * | title                   |
     * | 业务类型：type          |
     * | 业务时间：2015-03-12    |
     * | 业务内容：content       |
     * | remark                  |
     * --------------------------
     *
     * @param touser  接收方的openid
     * @param title   消息的抬头内容
     * @param type    消息类型
     * @param content 具体通知内容
     * @param remark  底部信息
     * @param url     消息点击后的访问连接，没有请传空字符串""
     * @return 发送成功返回true，失败返false
     */
    public static boolean sendBusinessNoticeMsg(String touser, String title, String type, String content, String remark, String url) {
        //oP1se1IxKwG6Su0y6K4Q-_3pkN_Y
        //获取系统当前时间，格式化为日期的显示格式
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //构造json参数
        String param = "{\"touser\":\"" + touser +
                "\",\"data\":{\"keyword3\":{\"color\":\"#173177\",\"value\":\"" +
                content + "\"},\"keyword1\":{\"color\":\"#173177\",\"value\":\"" +
                type + "\"},\"keyword2\":{\"color\":\"#173177\",\"value\":\"" +
                sdf.format(d) + "\"},\"remark\":{\"color\":\"#173177\",\"value\":\"" +
                remark + "\"},\"first\":{\"color\":\"#173177\",\"value\":\"" + title +
                "\"}},\"template_id\":\"" + WxConfig.BUSINESS_TEMPLATE_ID + "\",\"url\":\"" + url + "\"}";
        WeChat wechat = sendTemplatMsg(param);
        //判断消息是否发送成功
        if (wechat.getErrmsg().trim().equals("ok")) {
            return true;
        }
        return false;
    }

    /**
     * 发送审核通知消息
     * Usage:
     *       WeChatUtil.sendReviewNoticeMsg("oP1se1IxKwG6Su0y6K4Q-_3pkN_Y","RLDS你好！","XXXX项目",true,"审核通过","请完善资料后重新提交！","");
     * Template：
     * --------------------------
     * | title                   |
     * | 审核事项：desc          |
     * | 审核结果：resultString  |
     * | remark                  |
     * --------------------------
     * @param touser 接收方的openid
     * @param title 消息的抬头内容
     * @param desc 审核的内容描述
     * @param result 审核结果true成功,false失败（用于显示不同的颜色）
     * @param resultString 审核结果文本(如：审核通过)
     * @param remark 底部备注消息
     * @param url 消息点击后的访问连接，没有请传空字符串""
     * @return 成功返回true，失败返回false
     */
    public static boolean sendReviewNoticeMsg(String touser, String title, String desc, boolean result, String resultString, String remark, String url) {
        String color = "";
        //true为审核通过
        if (result == true) {
            //绿色
            color = "#32CD32";
        } else {
            //红
            color = "#DC143C";
        }
        //构造参数
        String param = "{" +
                "    \"touser\":\"" + touser + "\"," +
                "    \"template_id\":\"" + WxConfig.REVIEW_TEMPLATE_ID + "\"," +
                "    \"data\":{" +
                "        \"first\":{" +
                "            \"value\":\"" + title + "\"," +
                "            \"color\":\"#173177\"" +
                "        },\n" +
                "        \"keyword1\":{" +
                "            \"value\":\"" + desc + "\"," +
                "            \"color\":\"#173177\"" +
                "        }," +
                "        \"keyword2\":{" +
                "            \"value\":\"" + resultString + "\"," +
                "            \"color\":\"" + color + "\"" +
                "        }," +
                "        \"remark\":{" +
                "            \"value\":\"" + remark + "\"," +
                "            \"color\":\"#173177\"" +
                "        }" +
                "    }," +
                "    \"url\":\"" + url + "\"" +
                "}";
        WeChat wechat = sendTemplatMsg(param);
        //判断消息是否发送成功
        if (wechat.getErrmsg().trim().equals("ok")) {
            return true;
        }
        return false;
    }
}
