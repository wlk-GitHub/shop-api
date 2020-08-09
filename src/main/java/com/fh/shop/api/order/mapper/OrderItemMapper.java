package com.fh.shop.api.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.order.po.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    void batchInsert(List<OrderItem> orderItemList);
}
