package com.shop.service.log.exception;

import com.alibaba.fastjson.JSONObject;
import com.shop.service.log.entity.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(401);
        httpServletResponse.setHeader("Content-Type","application/json;charset=UTF-8");

        try{
            Throwable cause = e.getCause();
            if(cause instanceof InvalidTokenException){
                Result res = Result.end(401,"无效的token",e.getMessage());
                Object json = JSONObject.toJSON(res);
                httpServletResponse.getWriter().write(json.toString());
            }else{
                Result res = Result.end(401,"无token-log",e.getMessage());
                Object json = JSONObject.toJSON(res);
                httpServletResponse.getWriter().write(json.toString());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
