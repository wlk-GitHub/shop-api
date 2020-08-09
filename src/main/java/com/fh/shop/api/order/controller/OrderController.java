package com.fh.shop.api.order.controller;


import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.annotation.Idempotent;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.order.biz.OrderService;
import com.fh.shop.api.order.param.OrderParam;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Resource(name = "orderService")
    private OrderService orderService;

    @GetMapping("/generateOrderConfirm")
    @Check
    @Idempotent
    @ApiOperation("生成确认订单页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth",value = "头信息",required = true,type = "string",paramType = "header")
    })
    public ServerResponse generateOrderConfirm(HttpServletRequest request){
        MemberVo member = (MemberVo) request.getAttribute(SystemConstant.CURR_MEMBER);
        Long memberId = member.getId();
        return orderService.generateOrderConfirm(memberId);
    }


    @PostMapping("/generateOrder")
    @Check
    @ApiOperation("生成订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth",value = "头信息",required = true,type = "string",paramType = "header"),
            @ApiImplicitParam(name = "recipientId",value = "收件人id",required = true,type = "long",paramType = "query"),
            @ApiImplicitParam(name = "payType",value = "支付类型",required = true,type = "int",paramType = "query")
    })
    public ServerResponse generateOrder(HttpServletRequest request, OrderParam orderParam){
        MemberVo member = (MemberVo) request.getAttribute(SystemConstant.CURR_MEMBER);
        Long memberId = member.getId();
        orderParam.setMemberId(memberId);
        return orderService.generateOrder(orderParam);
    }

    @GetMapping("/getResult")
    @Check
    @ApiOperation("获取订单结果")
    public ServerResponse getResult(HttpServletRequest request){
        MemberVo member = (MemberVo) request.getAttribute(SystemConstant.CURR_MEMBER);
        Long memberId = member.getId();
        return orderService.getResult(memberId);
    }



}
