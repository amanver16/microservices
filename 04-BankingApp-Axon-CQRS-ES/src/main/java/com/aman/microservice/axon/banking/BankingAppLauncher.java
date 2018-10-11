package com.aman.microservice.axon.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingAppLauncher {

    public static void main(String[] args) {
        SpringApplication.run(BankingAppLauncher.class, args);
    }
}