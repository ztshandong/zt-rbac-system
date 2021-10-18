package com.zhangzhuorui.framework.rbacsystem.service.impl;

import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtRoleDeptInfo;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtMenuCodeEnum;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseServiceImpl;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleDeptInfoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
public class ZtRoleDeptInfoServiceImpl extends ZtRbacSimpleBaseServiceImpl<ZtRoleDeptInfo> implements IZtRoleDeptInfoService {

    @Override
    public String getTableName() {
        return "zt_role_dept_info";
    }

    @Override
    public ZtParamEntity<ZtRoleDeptInfo> ztBeforeSimpleInsert(ZtParamEntity<ZtRoleDeptInfo> ztParamEntity) throws Exception {
        ZtRoleDeptInfo entity = ztParamEntity.getEntity();
        Object otherParams = entity.getOtherParams();
        ZtRoleDeptInfo tmp4Del = new ZtRoleDeptInfo();
        if (otherParams.equals(ZtMenuCodeEnum.ROLE_MANAGE.name())) {
            tmp4Del.setRoleCode(entity.getRoleCode());
        }
        ZtParamEntity<ZtRoleDeptInfo> ztParamEntity4Del = getThisService().getInitZtParamEntityWithOutCount(tmp4Del);
        ztParamEntity4Del = getThisService().ztSimpleSelectProviderWithoutCount(ztParamEntity4Del);
        List<ZtRoleDeptInfo> delList = getThisService().getList(ztParamEntity4Del);
        if (delList.size() > 0) {
            ztParamEntity4Del.setEntityList(delList);
            getThisService().ztSimpleDeleteByPrimaryKeyBatch(ztParamEntity4Del);
        }

        return super.ztBeforeSimpleInsert(ztParamEntity);
    }

    @Override
    public ZtParamEntity<ZtRoleDeptInfo> ztBeforeSimpleInsertBatch(ZtParamEntity<ZtRoleDeptInfo> ztParamEntity) throws Exception {
        List<ZtRoleDeptInfo> entityList = ztParamEntity.getEntityList();
        if (entityList.size() > 0) {
            Object otherParams = entityList.get(0).getOtherParams();
            ZtRoleDeptInfo tmp4Del = new ZtRoleDeptInfo();
            ZtParamEntity<ZtRoleDeptInfo> ztParamEntity4Del = getThisService().getInitZtParamEntityWithOutCount(tmp4Del);
            if (otherParams.equals(ZtMenuCodeEnum.ROLE_MANAGE.name())) {
                List<String> roleCodes = entityList.stream().map(ZtRoleDeptInfo::getRoleCode).distinct().collect(Collectors.toList());
                ztParamEntity4Del.andIn(ZtRoleDeptInfo::getRoleCode, roleCodes);
            }
            ztParamEntity4Del = getThisService().ztSimpleSelectProviderWithoutCount(ztParamEntity4Del);
            List<ZtRoleDeptInfo> delList = getThisService().getList(ztParamEntity4Del);
            if (delList.size() > 0) {
                ztParamEntity4Del.setEntityList(delList);
                getThisService().ztSimpleDeleteByPrimaryKeyBatch(ztParamEntity4Del);
            }
        }

        return super.ztBeforeSimpleInsertBatch(ztParamEntity);
    }
}

