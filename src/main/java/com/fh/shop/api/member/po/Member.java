package com.fh.shop.api.member.po;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class Member implements Serializable {
    private Long id;
    private String memberName;
    private String password;
    private String realName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String mail;
    private String phone;
    private Long shengId;
    private Long shiId;
    private Long xianId;
    private String areaName;


}
