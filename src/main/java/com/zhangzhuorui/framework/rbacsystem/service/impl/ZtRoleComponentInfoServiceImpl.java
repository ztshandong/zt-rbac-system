package com.zhangzhuorui.framework.rbacsystem.service.impl;

import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtComponentInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtRoleComponentInfo;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtMenuCodeEnum;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseServiceImpl;
import com.zhangzhuorui.framework.rbacsystem.service.IZtComponentInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleComponentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ZtRoleComponentInfoServiceImpl extends ZtRbacSimpleBaseServiceImpl<ZtRoleComponentInfo> implements IZtRoleComponentInfoService {

    @Override
    public String getTableName() {
        return "zt_role_component_info";
    }

    @Autowired
    IZtComponentInfoService iZtComponentInfoService;

    @Override
    public ZtParamEntity<ZtRoleComponentInfo> ztBeforeSimpleInsert(ZtParamEntity<ZtRoleComponentInfo> ztParamEntity) throws Exception {
        ZtRoleComponentInfo entity = ztParamEntity.getEntity();
        Object otherParams = entity.getOtherParams();
        ZtRoleComponentInfo tmp4Del = new ZtRoleComponentInfo();
        if (otherParams.equals(ZtMenuCodeEnum.ROLE_MANAGE.name())) {
            tmp4Del.setRoleCode(entity.getRoleCode());
        }
        ZtParamEntity<ZtRoleComponentInfo> ztParamEntity4Del = getThisService().getInitZtParamEntityWithOutCount(tmp4Del);
        ztParamEntity4Del = getThisService().ztSimpleSelectProviderWithoutCount(ztParamEntity4Del);
        List<ZtRoleComponentInfo> delList = getThisService().getList(ztParamEntity4Del);
        if (delList.size() > 0) {
            ztParamEntity4Del.setEntityList(delList);
            getThisService().ztSimpleDeleteByPrimaryKeyBatch(ztParamEntity4Del);
        }

        return super.ztBeforeSimpleInsert(ztParamEntity);
    }

    @Override
    public ZtParamEntity<ZtRoleComponentInfo> ztBeforeSimpleInsertBatch(ZtParamEntity<ZtRoleComponentInfo> ztParamEntity) throws Exception {
        List<ZtRoleComponentInfo> entityList = ztParamEntity.getEntityList();
        if (entityList.size() > 0) {
            Object otherParams = entityList.get(0).getOtherParams();
            ZtRoleComponentInfo tmp4Del = new ZtRoleComponentInfo();
            ZtParamEntity<ZtRoleComponentInfo> ztParamEntity4Del = getThisService().getInitZtParamEntityWithOutCount(tmp4Del);
            if (otherParams.equals(ZtMenuCodeEnum.ROLE_MANAGE.name())) {
                List<String> roleCodes = entityList.stream().map(ZtRoleComponentInfo::getRoleCode).distinct().collect(Collectors.toList());
                ztParamEntity4Del.andIn(ZtRoleComponentInfo::getRoleCode, roleCodes);
            }
            ztParamEntity4Del = getThisService().ztSimpleSelectProviderWithoutCount(ztParamEntity4Del);
            List<ZtRoleComponentInfo> delList = getThisService().getList(ztParamEntity4Del);
            if (delList.size() > 0) {
                ztParamEntity4Del.setEntityList(delList);
                getThisService().ztSimpleDeleteByPrimaryKeyBatch(ztParamEntity4Del);
            }
        }
        ZtParamEntity<ZtComponentInfo> ztComponentInfoZtParamEntity = iZtComponentInfoService.ztSimpleSelectAll();
        List<ZtComponentInfo> allComponent = iZtComponentInfoService.getList(ztComponentInfoZtParamEntity);
        List<ZtComponentInfo> leafComponent = allComponent.stream().filter(t -> t.getLeafFlag()).collect(Collectors.toList());
        List<String> leafComponentCodes = leafComponent.stream().map(ZtComponentInfo::getThisCode).collect(Collectors.toList());
        entityList = entityList.stream().filter(t -> leafComponentCodes.contains(t.getComponentCode())).collect(Collectors.toList());
        ztParamEntity.setEntityList(entityList);
        return super.ztBeforeSimpleInsertBatch(ztParamEntity);
    }
}

