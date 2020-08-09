package com.fh.shop.api.interceptor;

import com.fh.shop.api.annotation.Idempotent;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.exception.TokenException;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class IdempotentInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否进行幂等性处理
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if(!method.isAnnotationPresent(Idempotent.class)){
            return true;
        }
        //获取头信息
        String tokenHeader = request.getHeader("x-token");
        if(StringUtils.isEmpty(tokenHeader)){
            throw new TokenException(ResponseEnum.TOKEN_IS_MISS);
        }
        //判断redis中是否存在该key
        boolean exist = RedisUtil.exist(tokenHeader);
        if(!exist){
            throw new TokenException(ResponseEnum.TOKEN_IS_ERROR);
        }
        Long flag = RedisUtil.delete(tokenHeader);
        if(flag==0){
            //证明不是第一次请求是重复的请求
            throw new TokenException(ResponseEnum.TOKEN_REQUEST_REPET);
        }
        //放行
        return true;
    }
}
