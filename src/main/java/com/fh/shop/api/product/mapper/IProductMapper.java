package com.fh.shop.api.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.product.po.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductMapper extends BaseMapper<Product> {
    @Update("update t_product set stock=stock-#{num} where id=#{goodsId} and stock>=#{num}")
    int updateStock(@Param("goodsId") Long goodsId, @Param("num") int num);
}
