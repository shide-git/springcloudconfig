package com.my.fg.controller;

import com.my.fg.service.SchedualServiceHi;
import com.my.fg.service.SchedualServiceRibbon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {

    @Autowired
    SchedualServiceHi schedualServiceHi;

    @Autowired
    SchedualServiceRibbon schedualServiceRibbon;

    @GetMapping(value = "/hi")
    public String sayHi(@RequestParam String name) {
        return schedualServiceHi.sayHiFromClientOne( name );
    }

    @GetMapping("/hello")
    public String sayHello(){
        return schedualServiceRibbon.sayHelloFromClientOne();
    }

    @GetMapping("/world")
    public String sayWorld(){
        return "feign world";
    }
}
