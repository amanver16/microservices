package com.aman.microservice.dockerdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello/docker")
public class HelloRestApi {

    @GetMapping
    public String sayHello(){
        return "Hello Docker World !!!";
    }

}