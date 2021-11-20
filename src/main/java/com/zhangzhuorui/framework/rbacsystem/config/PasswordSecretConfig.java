package com.zhangzhuorui.framework.rbacsystem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author :  张涛 zhangtao
 * @version :  1.0
 * @createDate :  2017-01-01
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@Configuration
@ConfigurationProperties(prefix = "password.secret")
public class PasswordSecretConfig {

    List<PasswordSecretEntity> list;

    public List<PasswordSecretEntity> getList() {
        return list;
    }

    public void setList(List<PasswordSecretEntity> list) {
        this.list = list;
    }
}
