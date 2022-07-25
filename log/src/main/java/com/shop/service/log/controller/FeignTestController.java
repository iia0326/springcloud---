package com.shop.service.log.controller;

import com.shop.service.log.entity.LogEntity;
import com.shop.service.log.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/feign")
@RestController
public class FeignTestController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/hello")
    public String hello(){
        return "hello"+port;
    }

    @PutMapping("/insert")
    public Result insert(@RequestBody LogEntity logEntity){
        System.out.println(logEntity);
        return Result.end(200,logEntity,"新增成功");
    }
}
