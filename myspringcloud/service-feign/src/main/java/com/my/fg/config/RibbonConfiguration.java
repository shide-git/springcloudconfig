package com.my.fg.config;


import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/* 策略类 	                  命名 	                说明
    RandomRule                  随机策略 	        随机选择 Server
    RoundRobinRule              轮询策略 	        按顺序循环选择 Server
    RetryRule                   重试策略 	        在一个配置时问段内当选择 Server 不成功，则一直尝试选择一个可用的 Server
    BestAvailableRule           最低并发策略 	    逐个考察 Server，如果 Server 断路器打开，则忽略，再选择其中并发连接最低的 Server
    AvailabilityFilteringRule   可用过滤策略 	    过滤掉一直连接失败并被标记为 circuit tripped 的 Server，
                                                    过滤掉那些高并发连接的 Server（active connections 超过配置的网值）
    ResponseTimeWeightedRule 	响应时间加权策略 	根据 Server 的响应时间分配权重。响应时间越长，权重越低，被选择到的概率就越低；
                                                    响应时间越短，权重越高，被选择到的概率就越高。这个策略很贴切，综合了各种因素，
                                                    如：网络、磁盘、IO等，这些因素直接影响着响应时间
    ZoneAvoidanceRule 	        区域权衡策略 	    综合判断 Server 所在区域的性能和 Server 的可用性轮询选择 Server，
                                                    并且判定一个 AWS Zone 的运行性能是否可用，剔除不可用的 Zone 中的所有 Server*/

//注意：以下方案对ribbon和feign两种调用方式都适用

//ribbon自定义方式一：全局覆盖
/*@Configuration
public class RibbonConfiguration {

    //定义全局ribbon的负载均衡为随机规则---覆盖掉默认的轮询规则

    @Bean
    public IRule ribbonRule() {
        return new RandomRule();
    }
}*/




//ribbon自定义方式二：单服务覆盖，使用注解
//A:添加配置类
/*@Configuration *//** 用来标记使用的注解，方便排除或者引用 **//*
@AvoidScan
public class RibbonRandomLoadBalancingConfiguration {
    @Resource
    IClientConfig clientConfig;

    @Bean
    public IRule ribbonRule(IClientConfig clientConfig) {
        return new RandomRule();
    }
}

//B:在启动类上添加注解
*//** 配置针对单个服务的 Ribbon 负载均衡策略 **//*
@RibbonClient( name = "demo-goods", configuration = RibbonRandomLoadBalancingConfiguration.class )
*//** 此处配置根据标识 @AvoidScan 过滤掉需要单独配置的 Ribbon 负载均衡策略，不然就会作用于全局，启动就会报错 *//*
@ComponentScan( excludeFilters = @ComponentScan.Filter( type = FilterType.ANNOTATION, value = AvoidScan.class ))*/




//ribbon自定义方式三：单服务覆盖，使用注解
//A:添加配置
/*### 针对单个服务的 Ribbon 配置
    demo-goods:
        ribbon: # 基于配置文件形式的 针对单个服务的 Ribbon 负载均衡策略
            NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
//B:在调用的service中注入LoadBalancerClient：
    @Autowired
    private LoadBalancerClient loadBalancerClient;

//C:在调用前选择配置的负载均衡服务：
    this.loadBalancerClient.choose("service-hi");
*/
