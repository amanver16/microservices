package com.aman.microservice.giftcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@EnableEurekaServer
@SpringBootApplication
public class GiftCardDiscoveryApp {

    public static void main(String[] args) {
        SpringApplication.run(GiftCardDiscoveryApp.class, args);
    }

}