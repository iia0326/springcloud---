package com.kangyi.feign.fallback;

import com.alibaba.fastjson.JSONObject;
import com.kangyi.entity.Result;
import com.kangyi.entity.LogEntity;
import com.kangyi.feign.TestFeignService;
import com.kangyi.service.LogService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestFeignServiceImpl implements TestFeignService {


    @Autowired
    private LogService2 logService2;

    @Override
    public String hello() {
        return "log-service暂时访问不通";
    }

//    @Override
//    public String insert(LogEntity logEntity) {
//        JSONObject res = new JSONObject();
//        res.put("code",500);
//        res.put("data",logEntity);
//        res.put("msg","log-service暂时访问不通");
//        return res.toJSONString();
//    }

    @Override
    public String insertLog(LogEntity logEntity) {
        System.err.println("远程日志写入失败");
        System.err.println("本地处理写入日志业务");
        Result res =  logService2.insertLog(logEntity);
        System.out.println(res);
        System.err.println("本地日志写入完毕");
        return JSONObject.toJSONString(res);
    }
}
