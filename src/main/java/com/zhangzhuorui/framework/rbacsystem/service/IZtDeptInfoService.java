package com.zhangzhuorui.framework.rbacsystem.service;

import com.zhangzhuorui.framework.rbacsystem.entity.ZtDeptInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import com.zhangzhuorui.framework.rbacsystem.extenduse.IZtRbacSimpleBaseService;

import java.util.List;

/**
 * 基于张涛极速开发框架的RBAC权限管理系统 服务类
 *
 * @author zhangtao
 * @version :  1.0
 * @createDate : 2017-01-01
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
public interface IZtDeptInfoService extends IZtRbacSimpleBaseService<ZtDeptInfo> {

    List<String> getCurUserDeptCodes(ZtUserInfo userInfo);

}


