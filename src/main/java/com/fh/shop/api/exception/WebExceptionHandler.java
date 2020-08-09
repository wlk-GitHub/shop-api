package com.fh.shop.api.exception;


import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class WebExceptionHandler {

    @ResponseBody
    @ExceptionHandler(GlobalException.class)
    public ServerResponse handlerGlobalException(GlobalException e){
        ResponseEnum responseEnum = e.getResponseEnum();
        return ServerResponse.error(responseEnum);
    }

    @ResponseBody
    @ExceptionHandler(TokenException.class)
    public ServerResponse handlerTokenException(TokenException e){
        ResponseEnum responseEnum = e.getResponseEnum();
        return ServerResponse.error(responseEnum);
    }

}
