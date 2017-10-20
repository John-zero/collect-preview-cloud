package com;

import com.util.ProcessUtil;
import de.codecentric.boot.admin.config.EnableAdminServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * https://docs.spring.io/spring-boot/docs/1.5.7.RELEASE/reference/htmlsingle/#production-ready-metrics
 *
 * https://github.com/codecentric/spring-boot-admin
 *
 * 参考：
 *  https://william8188.github.io/spring-boot-admin/preface/
 */
@SpringBootApplication
@EnableAdminServer
public class SpringBootAdminApplication extends SpringBootServletInitializer
{
    private static final Logger LOG = LogManager.getLogger(SpringBootAdminApplication.class);

    public static void main(String[] args)
    {
        SpringApplication.run(SpringBootAdminApplication.class, args);

        LOG.info(String.format("Starting Application on %s with ProcessName: %s, MainClass: %s, PID: %s !!!", ProcessUtil.gainHostName(), ProcessUtil.gainProcessName(), ProcessUtil.gainMainClass(), ProcessUtil.gainPID()));
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        application.listeners();

        return application.sources(applicationClass);
    }

    private static Class<SpringBootAdminApplication> applicationClass = SpringBootAdminApplication.class;

}