package com.wsa.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String instanceIp = getInstanceIp();

        registry.addMapping("/**")
                .allowedOrigins("http://" + instanceIp + ":5173")  // 使用动态IP设置allowedOrigins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
// 获取实例的IP地址
    private String getInstanceIp() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "localhost";  // 如果无法获取IP地址，回退到localhost
        }
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
