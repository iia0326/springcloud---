package com.shop.service.log;

//import com.kangyi.util.BannerBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.shop.service.log.mapper")
@SpringBootApplication
public class LogApplication {

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(LogApplication.class);
//        springApplication.setBanner(new BannerBuilder());
        springApplication.run(args);
    }

}
