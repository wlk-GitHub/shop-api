package com.fh.shop.api.pay.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.pay.biz.IPayService;
import io.swagger.annotations.Api;
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
@RequestMapping("/api/pay")
@Api(tags = "支付接口")
public class PayController {

    @Resource(name = "payService")
    private IPayService PayService;

    @PostMapping("/createNative")
    @Check
    @ApiOperation("统一下单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth",value = "头信息",required = true,type = "string",paramType = "header")
    })
    public ServerResponse createNative(HttpServletRequest request){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConstant.CURR_MEMBER);
        Long memberId = memberVo.getId();
        return PayService.createNative(memberId);
    }

    @GetMapping("/queryStatus")
    @Check
    @ApiOperation("查询支付状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth",value = "头信息",required = true,type = "string",paramType = "header")
    })
    public ServerResponse queryStatus(HttpServletRequest request){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConstant.CURR_MEMBER);
        Long memberId = memberVo.getId();
        return PayService.queryStatus(memberId);
    }
}
