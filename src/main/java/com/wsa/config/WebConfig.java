package com.wsa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
                // .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 添加对 D:/uploads 目录的资源映射
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///D:/uploads/")
                .setCachePeriod(0);

        // 保留对 classpath:/static/ 目录的资源映射
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(0);
    }
}
