package com.zhangzhuorui.framework.rbacsystem.config;

/**
 * @author :  张涛 zhangtao
 * @version :  1.0
 * @createDate :  2021/11/19 上午10:49
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
public class PasswordSecretEntity {

    private Long version;

    private String value;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
