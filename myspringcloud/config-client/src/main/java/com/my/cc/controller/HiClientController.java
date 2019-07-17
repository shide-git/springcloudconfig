package com.my.cc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiClientController {

    //http://localhost:8768/actuator/bus-refresh --- 配置刷新post请求

    @Value("${hello}")
    String hello;

    @RequestMapping(value = "/hi")
    public String hi(){
        return hello;
    }
}
