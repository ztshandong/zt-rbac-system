package com.zhangzhuorui.framework.rbacsystem.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author :  zhangtao
 * @version :  1.0
 * @createDate :  2021/8/16 18:55
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@Configuration
@EnableSwagger2
public class ZtSwaggerConfig {
    @Value("${swagger.enable:true}")
    Boolean enable;

    @Bean
    public Docket createRestApi() {

        Predicate<RequestHandler> predicate = RequestHandlerSelectors.withClassAnnotation(Api.class);
        predicate = Predicates.or(predicate, RequestHandlerSelectors.withClassAnnotation(ApiModel.class));
        predicate = Predicates.or(predicate, RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(predicate)
                .apis(RequestHandlerSelectors.basePackage("com.zhangzhuorui.framework"))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .paths(PathSelectors.regex("/.*"))
                .build()
                .pathMapping("/")
                .apiInfo(apiInfo())
                .enable(enable);
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("张涛", "https://ztshandong.github.io/", "17091648421@126.com");
        return new ApiInfoBuilder()
                .title("基于张涛极速开发框架的权限管理系统")
                .description("RBAC System Based On Zt-Framework")
                .termsOfServiceUrl("https://ztshandong.github.io/")
                .contact(contact)
                .version("1.0")
                .build();
    }
}


