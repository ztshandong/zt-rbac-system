package com.zhangzhuorui.framework.rbacsystem.service.impl;

import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtRoleUserInfo;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtMenuCodeEnum;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseServiceImpl;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleUserInfoService;
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
public class ZtRoleUserInfoServiceImpl extends ZtRbacSimpleBaseServiceImpl<ZtRoleUserInfo> implements IZtRoleUserInfoService {

    @Override
    public String getTableName() {
        return "zt_role_user_info";
    }

    @Override
    public ZtParamEntity<ZtRoleUserInfo> ztBeforeSimpleInsert(ZtParamEntity<ZtRoleUserInfo> ztParamEntity) throws Exception {
        ZtRoleUserInfo entity = ztParamEntity.getEntity();
        Object otherParams = entity.getOtherParams();
        ZtRoleUserInfo tmp4Del = new ZtRoleUserInfo();
        if (otherParams.equals(ZtMenuCodeEnum.ROLE_MANAGE.name())) {
            tmp4Del.setRoleCode(entity.getRoleCode());
        }
        ZtParamEntity<ZtRoleUserInfo> ztParamEntity4Del = getThisService().getInitZtParamEntityWithOutCount(tmp4Del);
        ztParamEntity4Del = getThisService().ztSimpleSelectProviderWithoutCount(ztParamEntity4Del);
        List<ZtRoleUserInfo> delList = getThisService().getList(ztParamEntity4Del);
        if (delList.size() > 0) {
            ztParamEntity4Del.setEntityList(delList);
            getThisService().ztSimpleDeleteByPrimaryKeyBatch(ztParamEntity4Del);
        }

        return super.ztBeforeSimpleInsert(ztParamEntity);
    }

    @Override
    public ZtParamEntity<ZtRoleUserInfo> ztBeforeSimpleInsertBatch(ZtParamEntity<ZtRoleUserInfo> ztParamEntity) throws Exception {
        List<ZtRoleUserInfo> entityList = ztParamEntity.getEntityList();
        if (entityList.size() > 0) {
            Object otherParams = entityList.get(0).getOtherParams();
            ZtRoleUserInfo tmp4Del = new ZtRoleUserInfo();
            ZtParamEntity<ZtRoleUserInfo> ztParamEntity4Del = getThisService().getInitZtParamEntityWithOutCount(tmp4Del);
            if (otherParams.equals(ZtMenuCodeEnum.ROLE_MANAGE.name())) {
                List<String> roleCodes = entityList.stream().map(ZtRoleUserInfo::getRoleCode).distinct().collect(Collectors.toList());
                ztParamEntity4Del.andIn(ZtRoleUserInfo::getRoleCode, roleCodes);
            }
            ztParamEntity4Del = getThisService().ztSimpleSelectProviderWithoutCount(ztParamEntity4Del);
            List<ZtRoleUserInfo> delList = getThisService().getList(ztParamEntity4Del);
            if (delList.size() > 0) {
                ztParamEntity4Del.setEntityList(delList);
                getThisService().ztSimpleDeleteByPrimaryKeyBatch(ztParamEntity4Del);
            }
        }

        return super.ztBeforeSimpleInsertBatch(ztParamEntity);
    }
}

