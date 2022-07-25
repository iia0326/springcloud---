package com.shop.service.log.exception;

import com.alibaba.fastjson.JSONObject;
import com.shop.service.log.entity.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(401);
        httpServletResponse.setHeader("Content-Type","application/json;charset=UTF-8");

        try{
            Throwable cause = e.getCause();
            Result res = Result.end(401,"权限不足",e.getMessage());
            Object json = JSONObject.toJSON(res);
            httpServletResponse.getWriter().write(json.toString());

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
