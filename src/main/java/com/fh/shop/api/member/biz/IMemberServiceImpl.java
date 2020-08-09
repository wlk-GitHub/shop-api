package com.fh.shop.api.member.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.mapper.IMemberMapper;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.rabbitmq.MQSender;
import com.fh.shop.api.rabbitmq.MailMessage;
import com.fh.shop.api.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;


@Service("memberService")
@Transactional(rollbackFor = Exception.class)
public class IMemberServiceImpl implements IMemberService {
    @Autowired
    private IMemberMapper memberMapper;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private MQSender mqSender;

    @Override
    public ServerResponse addMember(Member member) {
        String memberName = member.getMemberName();
        String password = member.getPassword();
        String mail = member.getMail();
        String phone = member.getPhone();
        if(StringUtils.isEmpty(memberName) || StringUtils.isEmpty(mail)
                                                       || StringUtils.isEmpty(password)
                                                       || StringUtils.isEmpty(phone)

        ){
            return ServerResponse.error(ResponseEnum.REG_MEMBER_IS_NULL);
        }

        //判断会员名是否唯一
        QueryWrapper<Member> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("memberName",memberName);
        Member member1 = memberMapper.selectOne(queryWrapper);
        if(member1!=null){
            return ServerResponse.error(ResponseEnum.REG_MEMBER_IS_EXIST);
        }


        //判断手机号是否唯一
        QueryWrapper<Member> phoneQueryWrapper =new QueryWrapper<>();
        phoneQueryWrapper.eq("phone",phone);
        Member member2 = memberMapper.selectOne(phoneQueryWrapper);
        if(member2!=null){
            return ServerResponse.error(ResponseEnum.REG_PHONE_IS_EXIST);
        }


        //判断邮箱是否唯一
        QueryWrapper<Member> mailQueryWrapper =new QueryWrapper<>();
        mailQueryWrapper.eq("mail",mail);
        Member member3 = memberMapper.selectOne(mailQueryWrapper);
        if(member3!=null){
            return ServerResponse.error(ResponseEnum.REG_MAIL_IS_EXIST);
        }

        //注册会员
        memberMapper.addMember(member);
        mailUtil.sendMail(mail,"注册成功!","恭喜---"+member.getMemberName()+"---会员注册成功！");
        return ServerResponse.success();
    }

    @Override
    public ServerResponse validateMemberName(String memberName) {
        if(StringUtils.isEmpty(memberName)){
            return ServerResponse.error(ResponseEnum.REG_MEMBER_IS_NULL);
        }
        //进行唯一性验证
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("memberName",memberName);
        Member memberDB = memberMapper.selectOne(queryWrapper);
        if(memberDB !=null){
            return ServerResponse.error(ResponseEnum.REG_MEMBER_IS_EXIST);
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse validatePhone(String phone) {
        //判断是否为空
        if(StringUtils.isEmpty(phone)){
            return ServerResponse.error(ResponseEnum.REG_MEMBER_IS_NULL);
        }
        //进行唯一性验证
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        Member memberPhone = memberMapper.selectOne(queryWrapper);
        if(memberPhone!=null){
            return ServerResponse.error(ResponseEnum.REG_PHONE_IS_EXIST);
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse validateMail(String mail) {
        if(StringUtils.isEmpty(mail)){
            return ServerResponse.error(ResponseEnum.REG_MEMBER_IS_NULL);
        }

        QueryWrapper<Member>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mail",mail);
        Member memberMail = memberMapper.selectOne(queryWrapper);
        if(memberMail!=null){
            return ServerResponse.error(ResponseEnum.REG_MAIL_IS_EXIST);
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse login(String memberName, String password) {

        //非空判断
       if(StringUtils.isEmpty(memberName) || StringUtils.isEmpty(password)){
            return ServerResponse.error(ResponseEnum.LOGIN__INFO_IS_NULL);
        }

        //解密
        memberName = RSAUtil.decrypt(memberName);
        password = RSAUtil.decrypt(password);
        //判断用户名是否存在
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("memberName", memberName);
        Member member = memberMapper.selectOne(memberQueryWrapper);
        if(member==null){
            return ServerResponse.error(ResponseEnum.LOGIN_NAME_NOT_EXIT);
        }

        //判断密码是否正确
        if(!password.equals(member.getPassword())){
            return ServerResponse.error(ResponseEnum.LOGIN_PASSWORD_ERROR);
        }


        //======================生成token=======================
        //模拟JWT[JSON WEB TOKEN]
        //生成token的样子类似与xxxx.yyy 用户信息.对用户信息的签名
        //签名的目的：保证用户信息不被篡改
        //怎么生成签名：md5(用户信息 结合 秘钥)
        //sign代表签名，secret/
        //秘钥是在服务器端保存的，黑客，攻击者不好获取到
        //======================================================

        //生成用户信息对应的json
        MemberVo memberVo = new MemberVo();
        Long memberId = member.getId();
        memberVo.setId(member.getId());
        memberVo.setMemberName(member.getMemberName());
        memberVo.setRealName(member.getRealName());
        String uuid = UUID.randomUUID().toString();
        memberVo.setUuid(uuid);
        //将java对象转为json
        String memberJson = JSONObject.toJSONString(memberVo);

        //对用户信息进行base64编码【起到一定的安全作用但是对于计算机专业人士起不到作用】
        try {
            String memberJsonBase64 = Base64.getEncoder().encodeToString(memberJson.getBytes("utf-8"));
            //生成用户信息所对应的签名
            String sign = Md5Util.sign(memberJsonBase64, Md5Util.SECRET);
            //对签名也进行base64编码
            String signBase64 = Base64.getEncoder().encodeToString(sign.getBytes("utf-8"));

            //处理超时
            RedisUtil.setEx(KeyUtil.buildMemberKey(uuid,memberId), "",KeyUtil.MEMBER_KEY_EXPIRE );

            //发送邮件
            String mail = member.getMail();
            MailMessage mailMessage = new MailMessage();
            mailMessage.setMail(mail);
            mailMessage.setTitle("登陆成功");
            mailMessage.setRealName(member.getRealName());
            mailMessage.setContent(member.getRealName()+"在"+ DateUtil.date2str(new Date(), DateUtil.FULL_TIME)+"登录了！");
            mqSender.sendMail(mailMessage);

            //响应数据给客户端
            return ServerResponse.success(memberJsonBase64+"."+signBase64);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ServerResponse.error();
        }



    }


}
