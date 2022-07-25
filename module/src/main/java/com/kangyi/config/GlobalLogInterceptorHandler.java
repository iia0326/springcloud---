package com.kangyi.config;

import com.alibaba.fastjson.JSONObject;
import com.kangyi.entity.LogEntity;
import com.kangyi.feign.TestFeignService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.util.Date;
import java.util.Map;

public class GlobalLogInterceptorHandler implements HandlerInterceptor {



    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if(ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");

        }

        if (ip == null || ip.length () == 0 || "unknown".equalsIgnoreCase (ip)) {
            ip = request.getHeader ("WL-Proxy-Client-IP");

        }

        if (ip == null || ip.length () == 0 || "unknown".equalsIgnoreCase (ip)) {
            ip = request.getRemoteAddr ();

            if (ip.equals ("127.0.0.1")) {
//根据网卡取本机配置的IP

                InetAddress inet = null;

                try {
                    inet = InetAddress.getLocalHost ();

                } catch (Exception e) {
                    e.printStackTrace ();

                }

                ip = inet.getHostAddress ();

            }

        }

// 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割

        if (ip != null && ip.length () > 15) {
            if (ip.indexOf (",") > 0) {
                ip = ip.substring (0, ip.indexOf (","));

            }

        }

        return ip;
    }

    @Autowired
    private TestFeignService testFeignService;

    //采用构造注入的方式,方便全局拦截器注入
    public GlobalLogInterceptorHandler(TestFeignService testFeignService) {
        this.testFeignService = testFeignService;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String path = request.getRequestURI();
        String method = request.getMethod();
        //返回值是在ResponseInceptor中装入request的Attribute中的
        Object responseBody = request.getAttribute("response");
        int statusCode = response.getStatus();
        String ip = getIpAddress(request);



        System.out.println("请求的method为:"+method);
        System.out.println("请求的状态码为:"+statusCode);
        System.out.println("请求的路径为:"+path);
        System.out.println("返回值为:"+JSONObject.toJSONString(responseBody));
        System.out.println("请求的ip地址为："+ip);
        Map<String, String[]> parameterMap = request.getParameterMap();
        String parameterJSON = JSONObject.toJSONString(parameterMap);
        System.out.println("请求的参数为："+parameterJSON);
        String accessToken = request.getHeader("Authorization");



        LogEntity log = new LogEntity();
        log.setTime(new Date());
        log.setIp(ip);
//        log.setStatusCode(statusCode);
        log.setMethod(method);
        log.setUrl(path);
        log.setRequest(parameterJSON);
        log.setRequest(JSONObject.toJSONString(responseBody));
        Cookie[] cookies = request.getCookies();
        if (cookies!=null&&cookies.length>0){
            for (Cookie c : cookies) {
                System.out.println(c.getName() + ": " + c.getValue());
                if(c.getName().equals( "userId" )){
                    log.setUserId( Long.valueOf( c.getValue() ) );
                }
            }
        }

        System.out.println(log);

        String res = testFeignService.insertLog(log);
        System.out.println("日志服务的返回结果：");
        System.out.println(res);
    }
}
