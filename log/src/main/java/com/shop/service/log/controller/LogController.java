package com.shop.service.log.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.service.log.entity.LogEntity;
import com.shop.service.log.entity.Result;
import com.shop.service.log.mapper.LogMapper;
import com.shop.service.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;

@RestController
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @PostMapping("/insert")
    public Result insert(@RequestBody LogEntity logEntity){
        System.out.println(logEntity);

        return logService.insertOne(logEntity,"com.shop.service.log.mapper.LogMapper.insert");
    }

    @GetMapping("/list/page")
    public Result getListForPage(
        @RequestParam(value = "pno",defaultValue = "1") int pno,
        @RequestParam(value = "psize",defaultValue = "10") int psize,
        @RequestParam(value = "beginTime",defaultValue = "") String beginTime,
        @RequestParam(value = "endTime",defaultValue = "") String endTime,
        @RequestParam(value = "method",defaultValue = "") String method,
        @RequestParam(value = "path",defaultValue = "") String path,
        @RequestParam(value = "userAccount",defaultValue = "") String userAccount
    ){
        System.out.println(beginTime);
        System.out.println(endTime);
        Page p = new Page<>(pno,psize);
        QueryWrapper q = new QueryWrapper();
        try{

            if(beginTime.trim().length()>0){
                beginTime = beginTime+" 00:00:00";
                q.apply("unix_timestamp(insert_time) >= unix_timestamp('"+beginTime+"')");
            }
            if(endTime.trim().length()>0){
                endTime = endTime+" 23:59:59";
                q.apply("unix_timestamp(insert_time) <= unix_timestamp('"+endTime+"')");
            }
            if(method.trim().length()>0){
                q.eq("method",method);
            }
            if(path.trim().length()>0){
                q.like("path",path);
            }
            if(userAccount.trim().length()>0){
                q.like("user_account",userAccount);
            }
        }catch (Exception e){

        }
        q.orderByDesc("insert_time");
        return logService.getListForPage(p,q, LogMapper.class);
    }
}
