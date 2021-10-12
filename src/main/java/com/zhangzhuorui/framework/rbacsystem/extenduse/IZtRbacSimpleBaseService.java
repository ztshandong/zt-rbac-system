package com.zhangzhuorui.framework.rbacsystem.extenduse;

import com.zhangzhuorui.framework.core.ZtPropertyFunc;
import com.zhangzhuorui.framework.mybatis.simplebaseservice.IZtSimpleBaseService;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtComponentInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtDeptInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;

import java.util.List;

/**
 * @author :  张涛 zhangtao
 * @version :  1.0
 * @createDate :  2017-01-01
 * @description : 扩展使用
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
public interface IZtRbacSimpleBaseService<T extends ZtRbacBasicEntity> extends IZtSimpleBaseService<T> {

    ZtUserInfo getSimpleUserInfoFromToken();

    ZtComponentInfo buildComponentTree(ZtComponentInfo parentNode, List<ZtComponentInfo> treeNodes);

    ZtDeptInfo buildDeptTree(ZtDeptInfo parentNode, List<ZtDeptInfo> treeNodes);

    /**
     * 部门编号字段名
     *
     * @param :
     * @return :  java.lang.String
     * @author :  zhangtao
     * @createDate :  2021/10/2 上午9:24
     * @description :  部门字段
     * @updateUser :
     * @updateDate :
     * @updateRemark :
     */
    default ZtPropertyFunc<T, ?> getDeptCodeField() {
        return null;
    }

    /**
     * 是否启用部门级别数据权限
     * 最终的sql就是 deptCode IN (a,b,c)
     *
     * @param :
     * @return :  java.lang.Boolean
     * @author :  zhangtao
     * @createDate :  2021/10/2 上午9:23
     * @description :  是否启用部门级别数据权限
     * @updateUser :
     * @updateDate :
     * @updateRemark :
     */
    default Boolean dataScopeDeptFlag() {
        return false;
    }

    /**
     * 用户编号字段名
     *
     * @param :
     * @return :  java.lang.String
     * @author :  zhangtao
     * @createDate :  2021/10/2 上午9:26
     * @description :
     * @updateUser :
     * @updateDate :
     * @updateRemark :
     */
    default ZtPropertyFunc<T, ?> getUserCodeField() {
        return null;
    }

    /**
     * 是否启用用户数据权限
     * 最终的sql就是 userCode IN (a,b,c)
     *
     * @param :
     * @return :  java.lang.Boolean
     * @author :  zhangtao
     * @createDate :  2021/10/2 上午9:24
     * @description :  是否启用用户数据权限
     * @updateUser :
     * @updateDate :
     * @updateRemark :
     */
    default Boolean dataScopeUserFlag() {
        return false;
    }

}
