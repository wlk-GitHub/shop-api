package com.fh.shop.api.category.biz;

import com.fh.shop.api.category.mapper.CategoryMapper;
import com.fh.shop.api.category.po.Category;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public ServerResponse queryCategoryList() {
        List<Category> categoryList1 = categoryMapper.selectList(null);
        return ServerResponse.success(categoryList1);
    }
}
