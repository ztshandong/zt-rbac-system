package com.zhangzhuorui.framework.rbacsystem.config;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.zhangzhuorui.framework.core.ZtBasicNumberIdEntity4Swagger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  zhangtao
 * @version :  1.0
 * @createDate :  2017-01-01
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@Configuration
@EnableSwagger2
public class ZtSwaggerConfig {

    @Autowired
    ZtJwtTokenUtil ztJwtTokenUtil;

    @Value("${swagger.enable:true}")
    Boolean enable;

    @Bean
    public Docket createRestApi(TypeResolver resolver) {

        Predicate<RequestHandler> predicate = RequestHandlerSelectors.withClassAnnotation(Api.class);
        predicate = Predicates.or(predicate, RequestHandlerSelectors.withClassAnnotation(ApiModel.class));
        predicate = Predicates.or(predicate, RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class));

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(predicate)
                // .apis(RequestHandlerSelectors.basePackage("com.zhangzhuorui.framework"))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .paths(PathSelectors.regex("/.*"))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemas())
                .securityContexts(securityContexts())
                .additionalModels(resolver.resolve(ZtBasicNumberIdEntity4Swagger.class))
                .additionalModels(resolver.resolve(List.class))
                .pathMapping("/")
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

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContextList = new ArrayList<>();
        List<SecurityReference> securityReferenceList = new ArrayList<>();
        securityReferenceList.add(new SecurityReference(ztJwtTokenUtil.getTokenHeader(), scopes()));
        securityContextList.add(SecurityContext
                .builder()
                .securityReferences(securityReferenceList)
                .forPaths(PathSelectors.any())
                .build()
        );
        return securityContextList;
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{new AuthorizationScope("global", "accessAnything")};
    }

    private List<ApiKey> securitySchemas() {
        List<ApiKey> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey(ztJwtTokenUtil.getTokenHeader(), ztJwtTokenUtil.getTokenHeader(), "header"));
        return apiKeyList;
    }
}


