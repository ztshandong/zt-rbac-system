package com.zhangzhuorui.framework.rbacsystem.service.impl;

import com.zhangzhuorui.framework.rbacsystem.entity.ZtButtonInfo;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseServiceImpl;
import com.zhangzhuorui.framework.rbacsystem.service.IZtButtonInfoService;
import org.springframework.stereotype.Service;

/**
 * 基于张涛极速开发框架的RBAC权限管理系统 服务实现类
 *
 * @author zhangtao
 * @version :  1.0
 * @createDate : 2017-01-01
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@Service
public class ZtButtonInfoServiceImpl extends ZtRbacSimpleBaseServiceImpl<ZtButtonInfo> implements IZtButtonInfoService {

    @Override
    public String getTableName() {
        return "zt_button_info";
    }

}

