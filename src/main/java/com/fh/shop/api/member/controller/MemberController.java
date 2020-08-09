package com.fh.shop.api.member.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.member.biz.IMemberService;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/members")
@Api(tags = "会员接口")
public class MemberController{
    @Resource(name = "memberService")
    private IMemberService memberService;


    //注册
    @PostMapping
    public ServerResponse addMember(Member member){
        return  memberService.addMember(member);
    }


    //验证
    @GetMapping("/validateMemberName")
    public ServerResponse validateMemberName(String memberName){
        return memberService.validateMemberName(memberName);
    }

    @GetMapping("/validatePhone")
    public ServerResponse validatePhone(String phone){
        return memberService.validatePhone(phone);
    }

    @GetMapping("/validateMail")
    public ServerResponse validateMail(String mail){
        return memberService.validateMail(mail);
    }


    @PostMapping("/login")
    @ApiOperation("会员登陆接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberName",value = "会员名",type = "string",required = true,paramType = "query"),
            @ApiImplicitParam(name = "password",value = "密码",type = "string",required = true,paramType = "query"),
    })
    public ServerResponse login(String memberName,String password){
        return memberService.login(memberName,password);
    }


    @GetMapping("/findMember")
    @Check
    @ApiOperation("获取会员信息")

    public ServerResponse findMember(HttpServletRequest request){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConstant.CURR_MEMBER);
        return ServerResponse.success(memberVo);
    }


    @GetMapping("/logout")
    @Check
    public ServerResponse logout(HttpServletRequest request){
        MemberVo memberVo = (MemberVo) request.getAttribute(SystemConstant.CURR_MEMBER);
        String uuid = memberVo.getUuid();
        Long memberId = memberVo.getId();

        //清楚redis中的数据
        RedisUtil.delete(KeyUtil.buildMemberKey(uuid, memberId));
        return ServerResponse.success();
    }




}
