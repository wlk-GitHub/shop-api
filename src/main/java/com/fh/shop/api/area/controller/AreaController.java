package com.fh.shop.api.area.controller;


import com.fh.shop.api.area.biz.IAreaServiceImpl;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/areas")
public class AreaController {
    @Resource(name = "areaService")
    private IAreaServiceImpl areaService;

    @GetMapping
    public ServerResponse findChilds(Long id){
        return areaService.findChilds(id);
    }
}
