package com.aman.banking.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class AccountQueryApp {

    public static void main(String[] args) {
        SpringApplication.run(AccountQueryApp.class, args);
    }

}