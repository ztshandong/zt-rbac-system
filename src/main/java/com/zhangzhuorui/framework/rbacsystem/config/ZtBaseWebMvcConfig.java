package com.zhangzhuorui.framework.rbacsystem.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.LinkedList;

@Configuration
// @DependsOn("")
public class ZtBaseWebMvcConfig implements WebMvcConfigurer, InitializingBean {

    @Autowired
    ZtJwtTokenUtil ztJwtTokenUtil;

    @Autowired
    private ZtCorsInterceptor ztCorsInterceptor;

    protected ZtCorsInterceptor getZtCorsInterceptor() {
        return ztCorsInterceptor;
    }

    @Autowired
    private ZtTokenBaseInterceptor ztTokenBaseInterceptor;

    protected ZtTokenBaseInterceptor getZtTokenBaseInterceptor() {
        return ztTokenBaseInterceptor;
    }

    @Value("${upload.path:xxx}")
    private String uploadPath;

    protected String getUploadPath() {
        return "uploadPath";
    }

    LinkedList<String> addPathPatterns4TokenInterceptor = new LinkedList<>(Arrays.asList("/**"));

    protected LinkedList<String> getAddPathPatterns4TokenInterceptor() {
        return addPathPatterns4TokenInterceptor;
    }

    LinkedList<String> excludePathPatterns4TokenInterceptor = new LinkedList<>(Arrays.asList(
            "/ZtIndex/login",
            "/ZtIndex/uniLogin",
            "/doc.html",
            "/v2/api-docs-ext",
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"));

    protected LinkedList<String> getExcludePathPatterns4TokenInterceptor() {
        return excludePathPatterns4TokenInterceptor;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        if (!"xxx".equals(getUploadPath())) {
//            registry.addResourceHandler("/static/**").addResourceLocations("file:" + getUploadPath() + "/");
//        }
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**").addResourceLocations("file:" + getUploadPath() + "/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 跨域拦截器需放在最上面
        registry.addInterceptor(getZtCorsInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(getZtTokenBaseInterceptor())
                .addPathPatterns(getAddPathPatterns4TokenInterceptor())
                .excludePathPatterns(getExcludePathPatterns4TokenInterceptor())
        ;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        excludePathPatterns4TokenInterceptor.addAll(ztJwtTokenUtil.getIgnoreUrlLists());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // registry.addConverter(new ZtDateConverter());
    }
}
