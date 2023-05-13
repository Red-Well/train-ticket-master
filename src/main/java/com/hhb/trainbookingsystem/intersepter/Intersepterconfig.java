package com.hhb.trainbookingsystem.intersepter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Intersepterconfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Intersepter())
                .addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/register")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/ticketManager/login")
                .excludePathPatterns("/ticketLogin")
                .excludePathPatterns("/managerLogin")
                .excludePathPatterns("/manager/login")
                .excludePathPatterns("/")
                .excludePathPatterns("/search")
                .excludePathPatterns("/pay/{id}")
                .excludePathPatterns("/api/**")
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**")
                .excludePathPatterns("/static/**");
    }
}
