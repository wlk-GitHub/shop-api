package com.fh.shop.api;

import com.fh.shop.api.util.RedisUtil;
import org.junit.Test;

public class TestRedis {

    @Test
    public void test1(){
//        RedisUtil.set("username","k");
//        String username = RedisUtil.get("username");
//        System.out.println(username);

        RedisUtil.delete("username");

    }
}
