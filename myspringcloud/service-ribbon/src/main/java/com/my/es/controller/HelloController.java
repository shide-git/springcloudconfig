package com.my.es.controller;

import com.my.es.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @GetMapping(value = "/hi")
    public String hi(@RequestParam String name) {
        return helloService.hiService( name );
    }

    @GetMapping("/hello")
    public String sayHello(){
        return helloService.sayHelloService();
    }

    @GetMapping("/world")
    public String sayWorld(){
        return "ribbon world";
    }
}

