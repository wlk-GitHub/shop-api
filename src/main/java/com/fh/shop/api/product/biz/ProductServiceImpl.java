package com.fh.shop.api.product.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.product.mapper.IProductMapper;
import com.fh.shop.api.product.po.Product;
import com.fh.shop.api.product.vo.ProductVo;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("productService")

public class ProductServiceImpl implements ProductService {
    @Autowired
    private IProductMapper iProductMapper;

    @Value("${stock.less}")
    private int stockLess;

    @Override
    public ServerResponse findHotList() {
        String hotProductList = RedisUtil.get("hotProductList");
        if(StringUtils.isNotEmpty(hotProductList)){
            //将json格式的字符串转化为java对象,如果转化为java中的集合则用parseArray
            //如果要转化为java中的对象则用parseObject
            List<ProductVo> productList = JSONObject.parseArray(hotProductList, ProductVo.class);
            return ServerResponse.success(productList);
        }


        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.select("id","productName","price","mainImagePath");
        productQueryWrapper.eq("isHot",1);
        productQueryWrapper.eq("status",1);
        List<Product> productList = iProductMapper.selectList(productQueryWrapper);
        //po转换vo
        List<ProductVo> productVoList = new ArrayList<>();
        for (Product product : productList) {
            ProductVo productVo = new ProductVo();
            productVo.setId(product.getId());
            productVo.setMainImagePath(product.getMainImagePath());
            productVo.setPrice(product.getPrice().toString());
            productVo.setProductName(product.getProductName());
            productVoList.add(productVo);
        }

        //往缓存中存一份
        //把java对象转换为json格式的字符串
        String hotProductListJson = JSONObject.toJSONString(productVoList);
        RedisUtil.set("hotProductList",hotProductListJson);
       // RedisUtil.setEx("hotProductList", hotProductListJson, 1*60);
        return ServerResponse.success(productVoList);
    }

    @Override
    public List<Product> findStockList() {
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.lt("stock",stockLess);
        List<Product> productList = iProductMapper.selectList(productQueryWrapper);
        return productList;
    }

    @Override
    public ServerResponse findProductList() {
        List<Product> productList = iProductMapper.selectList(null);
        return ServerResponse.success(productList);
    }
}
