package com.zhangzhuorui.framework.rbacsystem;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = false, exposeProxy = true)
public class RbacSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(RbacSystemApplication.class, args);
    }

    public static void codeGen(String[] args) throws Exception {
        Map<String, String> map = new HashMap<>();
        // map.put("ZtButtonInfo", "zt_button_info");
        // map.put("ZtComponentInfo", "zt_component_info");
        // map.put("ZtDeptInfo", "zt_dept_info");
        // map.put("ZtExcludeInfo", "zt_exclude_info");
        // map.put("ZtPostInfo", "zt_post_info");
        // map.put("ZtRoleComponentInfo", "zt_role_component_info");
        // map.put("ZtRoleDeptInfo", "zt_role_dept_info");
        // map.put("ZtRoleInfo", "zt_role_info");
        // map.put("ZtRolePostInfo", "zt_role_post_info");
        // map.put("ZtRoleUserInfo", "zt_role_user_info");
        // map.put("ZtSpecHide", "zt_spec_hide");
        // map.put("ZtSpecShow", "zt_spec_show");
        // map.put("ZtUserDeptInfo", "zt_user_dept_info");
        // map.put("ZtUserInfo", "zt_user_info");
        // map.put("ZtUserPostInfo", "zt_user_post_info");
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            ztGenCode(stringStringEntry.getKey(), stringStringEntry.getValue());
        }
    }

    private static void ztGenCode(String entityName, String tableName) throws Exception {
        String now = LocalDateTime.now().toString();
        String projectPath = System.getProperty("user.dir");

        //先根据其他代码生成器生成entity与xml。
        // 注意entity继承com.zhangzhuorui.framework.core.ZtBasicEntity<>并且添加范型
        // mapper继承com.zhangzhuorui.framework.mybatis.simplebasemapper.ZtSimpleBaseMapper
        // 然后改这几个变量后，生成mapper.java，service，serviceImpl，controller
        // String entityName = "ZtFrameUse3";//已有的java实体名称
        // String tableName = "zt_frame_use3";//表名
        String packageName = "com.zhangzhuorui.rbac.rbacsystem";//包名
        String author = "zhangtao";//姓名
        String comment = "基于张涛极速开发框架的RBAC权限管理系统";//表注释

        Map<String, Object> map = new HashMap<>();
        map.put("packageName", packageName);
        map.put("entityName", entityName);
        map.put("tableName", tableName);
        map.put("date", "2017-01-01");
        map.put("author", author);
        map.put("comment", comment);

        String controllerStr = compileTemplate("templates/ZtCodeGen/controller.java.vm", map);
        String controllerPath = projectPath + "/src/main/java/com/zhangzhuorui/rbac/rbacsystem/controller/" + entityName + "Controller.java";
        File controllerFile = new File(controllerPath);
        PrintStream ps = new PrintStream(new FileOutputStream(controllerFile));
        ps.println(controllerStr);

        String serviceStr = compileTemplate("templates/ZtCodeGen/service.java.vm", map);
        String servicePath = projectPath + "/src/main/java/com/zhangzhuorui/rbac/rbacsystem/service/I" + entityName + "Service.java";
        File serviceFile = new File(servicePath);
        ps = new PrintStream(new FileOutputStream(serviceFile));
        ps.println(serviceStr);

        String serviceImplStr = compileTemplate("templates/ZtCodeGen/serviceImpl.java.vm", map);
        String serviceImplPath = projectPath + "/src/main/java/com/zhangzhuorui/rbac/rbacsystem/service/impl/" + entityName + "ServiceImpl.java";
        File serviceImplFile = new File(serviceImplPath);
        ps = new PrintStream(new FileOutputStream(serviceImplFile));
        ps.println(serviceImplStr);

        String mapperStr = compileTemplate("templates/ZtCodeGen/mapper.java.vm", map);
        String mapperPath = projectPath + "/src/main/java/com/zhangzhuorui/rbac/rbacsystem/mapper/" + entityName + "Mapper.java";
        File mapperFile = new File(mapperPath);
        ps = new PrintStream(new FileOutputStream(mapperFile));
        ps.println(mapperStr);

    }

    private static String compileTemplate(String resourceName, Map<String, Object> context)
            throws IOException {
        ClassPathResource cp = new ClassPathResource(resourceName);
        if (!cp.exists()) {
            return "";
        }

        //添加依赖使用代码生成器
        // <dependency>
        //     <groupId>org.apache.velocity</groupId>
        //     <artifactId>velocity-engine-core</artifactId>
        //     <version>2.2</version>
        // </dependency>

        VelocityEngine vEngine = new VelocityEngine();
        vEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        vEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        vEngine.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        vEngine.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        vEngine.init();

        org.apache.velocity.Template template = vEngine.getTemplate(resourceName);
        VelocityContext velocityContext = new VelocityContext(context);

        Writer out = new StringWriter();
        template.merge(velocityContext, out);
        out.flush();
        return out.toString();

    }
}
