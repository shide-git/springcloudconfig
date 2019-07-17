package com.my.es.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;  //使用配置方式负载均衡策略

    @HystrixCommand(fallbackMethod = "hiServiceError")
    public String hiService(String name) {
        this.loadBalancerClient.choose("service-hi");//使用配置方式负载均衡策略
        return restTemplate.getForObject("http://service-hi/hi?name="+name,String.class);
    }

    private String hiServiceError(String name){
        return "hi ribbon " + name + " error";
    }

    public String sayHelloService() {
        return restTemplate.getForObject("http://service-feign/world", String.class);
    }
}
