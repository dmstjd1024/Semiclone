package com.semiclone.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfigurerImpl implements WebMvcConfigurer{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //모든 요청에 대해서
                .allowedMethods("PATCH")
                .allowedMethods("GET")
                .allowedMethods("POST")
                .allowedMethods("DELETE")
                .allowedMethods("PUT")
                .allowedOrigins("http://localhost:3000"); //허용할 오리진들
    }
}