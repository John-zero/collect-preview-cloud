package com;

import com.util.ProcessUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
@MapperScan("com.web.mapper")
public class Application extends SpringBootServletInitializer
{
    private static final Logger LOG = LogManager.getLogger(Application.class);

    public static void main(String[] args)
    {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(Application.class, args);

//        LOG.info(configurableApplicationContext.getBean(WebConfig.class));
//        LOG.info(configurableApplicationContext.getEnvironment().getProperty("dev.host"));

        LOG.info(String.format("Starting Application on %s with ProcessName: %s, MainClass: %s, PID: %s !!!", ProcessUtil.gainHostName(), ProcessUtil.gainProcessName(), ProcessUtil.gainMainClass(), ProcessUtil.gainPID()));
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        application.listeners();

        application.properties("spring.profiles.active=dev");

        return application.sources(applicationClass);
    }

    private static Class<Application> applicationClass = Application.class;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext applicationContext)
    {
        return args -> {

            LOG.info("Let's inspect the beans provided by Spring Boot.");

            String [] beanNames = applicationContext.getBeanDefinitionNames();

            Arrays.sort(beanNames);

            for (String beanName : beanNames)
            {
                LOG.info("Spring Boot Bean Name : " + beanName);
            }
        };
    }

}