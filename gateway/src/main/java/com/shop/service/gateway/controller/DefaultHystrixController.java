package com.shop.service.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认降级处理，当转发的服务节点全部宕机之后会触发熔断方案使用该controller返回信息
 */
@RestController
public class DefaultHystrixController {

    @RequestMapping("/defaultfallback")
    public Map<String,String> defaultfallback(){
        System.out.println("降级操作...");
        Map<String,String> map = new HashMap<>();
        map.put("code","500");
        map.put("msg","请求的服务节点全部失效");
        map.put("data","null");
        return map;
    }
}
