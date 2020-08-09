package com.fh.shop.api.category.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.category.po.Category;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;

@Mapper
@Resource
public interface CategoryMapper extends BaseMapper<Category> {
}
