package com.shop.dashboard;

//import com.kangyi.util.BannerBuilder;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//服务端搭建Springboot Admin
@EnableAdminServer
@SpringBootApplication
public class DashboardApplication {

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(DashboardApplication.class);
//        springApplication.setBanner(new BannerBuilder());
        springApplication.run(args);
    }

}
