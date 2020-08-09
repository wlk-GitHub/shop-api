package com.fh.shop.api.category.controller;


import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.category.biz.CategoryService;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Resource(name = "categoryService")
    private CategoryService categoryService;


    @GetMapping
    public ServerResponse queryCategoryList(){
        return categoryService.queryCategoryList();
    }



}
