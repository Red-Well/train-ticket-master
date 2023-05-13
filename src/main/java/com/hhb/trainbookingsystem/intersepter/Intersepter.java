package com.hhb.trainbookingsystem.intersepter;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Intersepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("user")!=null){
            return  true;
        }
        if(request.getSession().getAttribute("Administrator")!=null){
            return  true;
        }
        if(request.getSession().getAttribute("ticketManager")!=null){
            return  true;
        }
        return false;
    }
}