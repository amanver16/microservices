package com.aman.microservice.hello;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@EnableAutoConfiguration
public class HelloController {

    @GetMapping
    public String sayHello() {
        return "Hello World";
    }

}