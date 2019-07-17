package com.my.fg.service.hystrix;

import com.my.fg.service.SchedualServiceHi;
import org.springframework.stereotype.Component;

@Component
public class SchedualServiceHiImpl implements SchedualServiceHi {

    @Override
    public String sayHiFromClientOne(String name) {
        return "hi, feign " + name + " error";
    }
}
