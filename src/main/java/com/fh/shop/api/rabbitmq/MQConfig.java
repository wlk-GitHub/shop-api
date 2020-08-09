package com.fh.shop.api.rabbitmq;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    public static final String MAIL_EXCHANGE = "MailExchange";
    public static final String MAIL_QUEUE = "MailQueue";
    public static final String MAIL_ROUTE_KEY = "mail";


    public static final String ORDER_EXCHANGE = "orderExchange";
    public static final String ORDER_QUEUE = "orderQueue";
    public static final String ORDER_ROUTE_KEY = "order";

    //创建交换机
    @Bean
    public DirectExchange orderExchange(){
        return new DirectExchange(ORDER_EXCHANGE,true,false);
    }

    //创建队列
    @Bean
    public Queue orderQueue(){
        return new Queue(ORDER_QUEUE,true);
    }

    //将队列和交换机绑定
    @Bean
    public Binding orderBinding(){
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(ORDER_ROUTE_KEY);
    }



    //创建交换机
    @Bean
    public DirectExchange mailExchange(){
        return new DirectExchange(MAIL_EXCHANGE,true,false);
    }

    //创建队列
    @Bean
    public Queue mailQueue(){
        return new Queue(MAIL_QUEUE,true);
    }

    //将队列和交换机绑定
    @Bean
    public Binding mailBinding(){
        return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(MAIL_ROUTE_KEY);
    }






}
