package com.fh.shop.api;

import com.fh.shop.api.rabbitmq.MQSender;
import com.fh.shop.api.rabbitmq.ProductsMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShopApiApplicationTests {

    @Test
    void  contextLoads(){

    }

/*
    @Autowired
    private MQSender mqSender;

    @Test
    public void test1(){
        for (int i = 1; i <= 10; i++) {
            ProductsMessage productsMessage = new ProductsMessage();
            productsMessage.setId(Long.parseLong(i+""));
            productsMessage.setPrice("300"+i);
            productsMessage.setStock(10L);
            mqSender.sendMail(productsMessage);
        }
s
    }*/

}
