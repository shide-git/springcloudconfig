package com.my.fg;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients() //开启feign功能，指定扫描的包，否则添加断路器之后，注入有问题
//@EnableHystrix 使用注解打开无效
@EnableHystrixDashboard
@EnableTurbine
public class FeignApp {
    public static void main(String[] args) {
        SpringApplication.run(FeignApp.class, args);
    }

    //添加该bean,解决无法连接到stream问题，同时dashbord界面填写的应该是http://localhost:8764/actuator/hystrix.steam
    //如果使用springboot2.0 和spring cloud Finchley.M8 版本搭建 使用（/actuator/hystrix.stream  而不是/hystrix.stream 为插入点）
    @Bean
    public ServletRegistrationBean getServlet(){
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream"); // -------/actuator/hystrix.stream
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}
