package com.shop.service.gateway;

//import com.kangyi.util.BannerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(GatewayApplication.class);
//        springApplication.setBanner(new BannerBuilder());
        springApplication.run(args);
    }

}
