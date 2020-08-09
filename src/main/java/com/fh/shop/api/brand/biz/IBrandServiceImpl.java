package com.fh.shop.api.brand.biz;

import com.fh.shop.api.brand.mapper.IBrandMapper;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("brandService")
@Transactional(rollbackFor = Exception.class)
public class IBrandServiceImpl implements IBrandService {

    @Autowired
    private IBrandMapper iBrandMapper;


    @Override
    public ServerResponse addBrand(Brand brand) {
        iBrandMapper.addBrand(brand);
        return ServerResponse.success();
    }


    @Override
    @Transactional(readOnly = true)
    public ServerResponse findList() {
        List<Brand> brandList = iBrandMapper.findList();
        return ServerResponse.success(brandList);
    }

    @Override
    public ServerResponse delete(Integer id) {
        iBrandMapper.delete(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse update(Brand brand) {
        iBrandMapper.update(brand);
        return ServerResponse.success() ;
    }

    @Override
    public ServerResponse deleteBatch(String ids) {
        if(StringUtils.isNotEmpty(ids)){
            String [] idArr = ids.split(",");
            List<Long> idList = Arrays.stream(idArr).map(x -> Long.parseLong(x)).collect(Collectors.toList());
            iBrandMapper.deleteBatch(idList);
        }
        return ServerResponse.success();
    }


}
