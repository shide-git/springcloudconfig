package com.my.es;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@EnableHystrixDashboard //访问http://localhost:8764/hystrix
@EnableTurbine
public class RibbonApp {

    public static void main(String[] args) {
        SpringApplication.run(RibbonApp.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    //添加该bean,解决无法连接到stream问题，同时dashbord界面填写的应该是http://localhost:8764/actuator/hystrix.steam
    //如果使用springboot2.0 和spring cloud Finchley.M8 版本搭建 使用（/actuator/hystrix.stream  而不是/hystrix.stream 为插入点）
    @Bean
    public ServletRegistrationBean getServlet(){
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream"); //-----------/actuator/hystrix.stream
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}
