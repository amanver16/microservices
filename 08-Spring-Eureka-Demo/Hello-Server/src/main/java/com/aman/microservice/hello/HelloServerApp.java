package com.aman.microservice.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class HelloServerApp {

    public static void main(String[] args) {
        SpringApplication.run(HelloServerApp.class, args);
    }

}