package com.zhangzhuorui.framework.rbacsystem.service;

import com.zhangzhuorui.framework.rbacsystem.entity.ZtComponentInfo;
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
public interface IZtComponentInfoService extends IZtRbacSimpleBaseService<ZtComponentInfo> {

    List<String> getCurUserLeafComponentCodes(ZtUserInfo userInfo);

    List<String> getCurUserAllComponentCodes(ZtUserInfo userInfo);

}


