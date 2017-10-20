package com.swagger;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * https://github.com/springfox/springfox/blob/master/docs/transitioning-to-v2.md
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig
{

    @Bean
    public Docket createRestAPI ()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("V1.0-Api-Docs")
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.web.control"))
                .paths(paths())
                .build()
                .apiInfo(apiInfo());
    }

    private Predicate<String> paths ()
    {
//        return PathSelectors.any();
        return or(regex("/*"));
    }

    private ApiInfo apiInfo ()
    {
        Contact contact = new Contact("John", "https://github.com/John-zero", "2625210655@qq.com");

        return new ApiInfoBuilder()
                .title("收藏云预览")
                .description("浏览器收藏夹, 在线预览, 爬虫抓取")
                .version("1.0")
                .contact(contact)
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }

}
