package com.web.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice // 主要处理的就是 Controller 层的错误信息, 而没有进入 Controller 层的错误 @ControllerAdvice 是无法处理的
public class GlobalExceptionHandler
{

    @ResponseBody
    public ExceptionInfo<String> exceptionHandler(HttpServletRequest httpServletRequest, Exception exception) throws Exception
    {
        ExceptionInfo<String> exceptionInfo = new ExceptionInfo<>();

        exceptionInfo.setCode("500");
        exceptionInfo.setDesc(exception.getMessage());
        exceptionInfo.setUrl(httpServletRequest.getRequestURI().toString());

        return exceptionInfo;
    }

}
