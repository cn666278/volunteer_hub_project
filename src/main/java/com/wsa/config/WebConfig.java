package com.wsa.config;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String instanceIp = getExternalIp();
        System.out.println("External IP: " + instanceIp);
        registry.addMapping("/**")
                .allowedOrigins("http://10.72.102.12:5173")  // 使用动态获取的外部IP设置allowedOrigins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    // 获取实例的外部IP地址
    private String getExternalIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) {
                        String ip = inetAddress.getHostAddress();
                        if (ip.startsWith("172.") || ip.startsWith("192.168.")) {
                            continue; // 跳过私有IP，寻找外部IP
                        }
                        return ip; // 返回外部IP
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "localhost";  // 如果无法获取外部IP地址，回退到localhost
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
