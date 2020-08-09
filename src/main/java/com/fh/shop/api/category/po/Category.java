package com.fh.shop.api.category.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_category")
public class Category implements Serializable {

    private Long id;
    private Long pid;
    private Integer type;
    private String categoryName;

}
