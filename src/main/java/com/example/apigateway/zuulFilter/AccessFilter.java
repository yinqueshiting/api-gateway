package com.example.apigateway.zuulFilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 继承ZuulFilter
 * @create: 2019-12-26 10:18
 **/
@Slf4j
public class AccessFilter extends ZuulFilter {
    /*
        过滤器类型、决定在请求的哪个生命周期中执行，
     */
    @Override
    public String filterType() {
        return "pre";
    }
    /*
        过滤器执行顺序
     */
    @Override
    public int filterOrder() {
        return 0;
    }
    /*
        判断该过滤器是否需要被执行
        true：对所有请求都生效；可以利用该函数来指定过滤器的有效范围。
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }
    /*
        过滤器的具体逻辑，
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        //try {
            HttpServletRequest httpServletRequest = requestContext.getRequest();
            log.info("send {} request to {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURL());
            String accessToken = (String) httpServletRequest.getParameter("accessToken");
            if (StringUtils.isBlank(accessToken)) {
                log.warn("未携带安全验证");
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(401);
                return null;
            }
            log.info("接收到的token:{}", accessToken);
            throwExcetion();
      /*  }catch (Exception ex){
            log.info("遭遇到了exception!!!");
            requestContext.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            requestContext.set("error.exception",ex);
        }*/
        return null;
    }

    public void throwExcetion(){
        log.info("进入throwExcetion（）");
        throw new RuntimeException("throw runtime exception");
    }
}
