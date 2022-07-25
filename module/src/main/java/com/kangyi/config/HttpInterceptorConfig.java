package com.kangyi.config;

import com.kangyi.feign.TestFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HttpInterceptorConfig implements WebMvcConfigurer {

    //在Configuration的类中自动注入一定能成功，
    // 所以我们采用构造注入的方式将这个TestFeignService动态的传入到拦截器中
    @Autowired
    private TestFeignService testFeignService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //将我们自定义拦截器GlobalLogInterceptorHandler添加到全局拦截器中
        //采用构造注入的方式
        InterceptorRegistration registration = registry.addInterceptor(new GlobalLogInterceptorHandler(testFeignService));
//        InterceptorRegistration registration2 = registry.addInterceptor(new CrosInterceptor());
        //监控该服务下的所有请求
        registration.addPathPatterns("/**");
//        registration2.addPathPatterns("/**");
//        System.out.println(" addInterceptors ################################################");

    }
}
