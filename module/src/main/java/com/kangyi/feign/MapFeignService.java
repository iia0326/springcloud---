package com.kangyi.feign;

import com.kangyi.feign.fallback.MapFeignServiceImpl;
//import com.shop.service.module.entity.LogEntity;
//import TestFeignServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

//FeignClient表示配置调用的服务，name配置调用服务的application-name
//将TestFeignServiceImpl这个类作为feign失败的降级处理方案，
// 当feign访问log-service失败的时候就会执行实现类中对应的方法返回plainB数据
@FeignClient(name = "map-service",fallback = MapFeignServiceImpl.class)
public interface MapFeignService {

    //这里的GetMapping代表请求对应服务的接口地址，要求与对应服务配置的path相同，返回类型根据需求设置即可，这里由于接口返回的就是一个字符串所以我们采用字符串作为返回类型
    @GetMapping("/feign/hello")
    public String hello();

//调用log-service中的/feign/insert并且实现传递logEntity
//    @PutMapping("/feign/insert")
//    //这里依然使用String 类型作为返回值是因为在log-service中引入的Result对象
//    // 是log-service项目中单独定义的返回值对象与我们其他服务中引入的Result不同属一个包
//    // 所以如果用Result对象作为返回类型会由于包名不一致导致异常，
//    // 这里我们采用字符串接收返回值再通过json化处理
//    public String insert(@RequestBody LogEntity logEntity);
//
//    @PostMapping("/insert")
//    String insertLog(@RequestBody() LogEntity logEntity);
//

}
