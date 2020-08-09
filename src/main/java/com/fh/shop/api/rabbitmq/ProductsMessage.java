package com.fh.shop.api.rabbitmq;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductsMessage implements Serializable {
    private Long id;

    private String price;

    private Long stock;

}
