package com.aman.microservice.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/hello/rest")
@EnableAutoConfiguration
public class HelloClientController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String getHelloMessage() {
        String url = "http://hello-server/hello";
        return restTemplate.getForObject(url, String.class);
    }

}