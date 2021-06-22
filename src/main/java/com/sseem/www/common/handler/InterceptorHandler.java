package com.sseem.www.common.handler;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InterceptorHandler extends HandlerInterceptorAdapter{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        System.out.println("controller 이벤트 호출 전");
        System.out.println("Interceptor pre !!!");

        System.out.println("request.getQueryString() ==>"+ request.getQueryString());
        System.out.println("request.getContextPath() ==>"+ request.getContextPath());
        System.out.println("request.getRequestURI() ==>"+ request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
//        System.out.println("controller 이벤트 호출 후 view 페이지 출력 전");
//        System.out.println("Interceptor post !!!");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception{
//        System.out.println("controller 이벤트 호출 후 view 페이지 출력 후");
//        System.out.println("Interceptor after !!!");
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
//        System.out.println("Interceptor afterConcurrentHand !!!");
    }

}
