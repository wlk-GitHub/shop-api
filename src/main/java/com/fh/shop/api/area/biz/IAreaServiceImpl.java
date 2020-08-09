package com.fh.shop.api.area.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.area.mapper.IAreaMapper;
import com.fh.shop.api.area.po.Area;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("areaService")
public class IAreaServiceImpl implements IAreaService {
    @Autowired
    private IAreaMapper areaMapper;

    @Override
    public ServerResponse findChilds(Long id) {
        QueryWrapper<Area> areaQueryWrapper = new QueryWrapper<>();
        areaQueryWrapper.eq("pid",id);
        List<Area> areas = areaMapper.selectList(areaQueryWrapper);
        return ServerResponse.success(areas);
    }
}
