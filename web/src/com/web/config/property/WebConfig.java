package com.web.config.property;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:web.properties")
public class WebConfig
{

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigure()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
