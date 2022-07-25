package com.kangyi;

import com.kangyi.config.NacosConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableCaching
@EnableTransactionManagement
//@ComponentScan({"com.*"})
@MapperScan(basePackages = "com.kangyi.mapper")
public class MainApplication   extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run( MainApplication.class, args );
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources( MainApplication.class );
    }

//    @Bean
//    public NacosConfig nacosConfig(){
//        return new NacosConfig( );
//    }


}
