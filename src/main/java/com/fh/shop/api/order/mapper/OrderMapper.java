package com.fh.shop.api.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.order.po.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends BaseMapper<Order> {
}
