package com.my.fg.service;

import com.my.fg.service.hystrix.SchedualServiceHiImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-ribbon")
public interface SchedualServiceRibbon {

    @RequestMapping(value = "/world",method = RequestMethod.GET)
    String sayHelloFromClientOne();
}
