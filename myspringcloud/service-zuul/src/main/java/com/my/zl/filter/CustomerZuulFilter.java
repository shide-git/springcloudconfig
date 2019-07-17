package com.my.zl.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomerZuulFilter extends ZuulFilter {

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
        return 0;
    }

    //当前ZuulFilter是否执行，true---执行，false---不执行，里面也可以执行逻辑
    @Override
    public boolean shouldFilter() {
        return true;
    }

    //ZuulFilter的逻辑执行方法
    @Override
    public Object run() throws ZuulException {
        System.out.println("filter order 000000000");
        RequestContext cxt = RequestContext.getCurrentContext();
        HttpServletRequest req = cxt.getRequest();
        System.out.println(req.getRequestURL());
        String url = req.getRequestURL().toString();
        if(url.contains("api-a")){
            try {
                cxt.setSendZuulResponse(false);
                cxt.setResponseStatusCode(401);
                cxt.getResponse().getWriter().write("禁止访问");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }
}
