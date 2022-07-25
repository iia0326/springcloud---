package com.kangyi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableDiscoveryClient
@EnableCaching
@EnableTransactionManagement
@SpringBootApplication
@MapperScan(basePackages = "com.kangyi.mapper")

public class BokeApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run( BokeApplication.class, args );
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources( BokeApplication.class );
    }

}
