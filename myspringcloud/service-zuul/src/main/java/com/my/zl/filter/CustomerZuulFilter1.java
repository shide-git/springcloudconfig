package com.my.zl.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

@Component
public class CustomerZuulFilter1 extends ZuulFilter {

    //定义四种filterType:
    //pre:路由之前过滤
    //routing:路由时过滤
    //post:路由之后过滤
    //error:错误之时过滤
    @Override
    public String filterType() {
        return "pre";
    }

    //filter的执行顺序，在同种filterType的ZuulFilter中，filterOrder越小，越先执行run方法
    @Override
    public int filterOrder() {
        return 1;
    }

    //当前ZuulFilter是否执行，true---执行，false---不执行
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("filter order 11111111111");
        return null;
    }
}
