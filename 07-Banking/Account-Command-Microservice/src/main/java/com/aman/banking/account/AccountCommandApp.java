package com.aman.banking.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AccountCommandApp {

    public static void main(String[] args) {
        SpringApplication.run(AccountCommandApp.class, args);
    }

}