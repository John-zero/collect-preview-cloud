package com.web.config;

import com.web.filter.AuthorizeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter
{

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
    {

    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer)
    {

    }

    /**
     * Spring Boot 自动配置不会自动把 swagger-ui.html 路径映射到对应的目录 /META-INF/resources/ 下, 所以需要手动配置映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        // 默认配置 /** 映射到 /static 或者 /public 或者 /resources 或者 /META-INF/resources

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        // 默认配置 /webjars/** 映射到 classpath:/META-INF/resources/webjars/

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public FilterRegistrationBean filterRegistration()
    {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AuthorizeFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("AuthorizeFilter");
        registration.setOrder(1);
        return registration;
    }

}
