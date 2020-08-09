package com.fh.shop.api.token.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.token.biz.ITokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/token")
@Api(tags = "生成token的接口")
public class TokenController {

    @Resource(name = "tokenService")
    private ITokenService tokenService;
    @PostMapping("/createToken")
    @ApiOperation("创建token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth",value = "头信息",required = true,type = "string",paramType = "header")
    })
    @Check
    public ServerResponse createToken(){
        return tokenService.createToken();
    }
}
