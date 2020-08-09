package com.fh.shop.api.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQSender {
    @Autowired
    public AmqpTemplate amqpTemplate;

    public void sendMail(MailMessage mailMessage){
        String mailJson = JSONObject.toJSONString(mailMessage);
        amqpTemplate.convertAndSend(MQConfig.MAIL_EXCHANGE,MQConfig.MAIL_ROUTE_KEY,mailJson);
    }
}
