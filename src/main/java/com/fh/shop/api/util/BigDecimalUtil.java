package com.fh.shop.api.util;

import java.math.BigDecimal;

public class BigDecimalUtil {

    //乘法
    public static BigDecimal mul(String s1,String s2){
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2= new BigDecimal(s2);
        //setScale最终保留几位小数
        return b1.multiply(b2).setScale(2);
    }

    //加法
    public static BigDecimal add(String s1,String s2){
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.add(b2);
    }


}
