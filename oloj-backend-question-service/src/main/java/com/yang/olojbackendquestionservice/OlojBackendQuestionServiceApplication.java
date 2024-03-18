package com.yang.olojbackendquestionservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.yang.olojbackendquestionservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.yang")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.yang.olojbackendserviceclient.service"})

public class OlojBackendQuestionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OlojBackendQuestionServiceApplication.class, args);
    }

}
