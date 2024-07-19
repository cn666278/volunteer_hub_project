package com.wsa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.pagehelper.PageHelper;
import java.util.Properties;

@Configuration
public class PageHelperConfig {

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
