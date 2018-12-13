package com.aman.microservice.giftcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GiftCardCommandApp {

    public static void main(String[] args) {
        SpringApplication.run(GiftCardCommandApp.class, args);
    }

}