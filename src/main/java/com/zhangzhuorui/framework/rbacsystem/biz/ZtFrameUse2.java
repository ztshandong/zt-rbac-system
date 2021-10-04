package com.zhangzhuorui.framework.rbacsystem.biz;

import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacBasicEntity;

/**
 * @author :  张涛 zhangtao
 * @version :  1.0
 * @createDate :  2017-01-01
 * @description : 开发框架示例 常规用法
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
public class ZtFrameUse2 extends ZtRbacBasicEntity<Integer> {
    /**
     * 字段1
     */
    private String udf1;

    /**
     * 数据权限：用户编号
     */
    private String userCode;

    public String getUdf1() {
        return udf1;
    }

    public void setUdf1(String udf1) {
        this.udf1 = udf1;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}