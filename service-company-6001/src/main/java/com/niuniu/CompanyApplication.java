package com.niuniu;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@MapperScan(basePackages = "com.niuniu.mapper")
@EnableDiscoveryClient //开启服务注册和发现
public class CompanyApplication {
    public static void main(String[] args) {

        SpringApplication.run(CompanyApplication.class,args);
    }
}
