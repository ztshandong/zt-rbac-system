package com.zhangzhuorui.framework.rbacsystem.service.impl;

import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtRolePostInfo;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtMenuCodeEnum;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseServiceImpl;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRolePostInfoService;
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
public class ZtRolePostInfoServiceImpl extends ZtRbacSimpleBaseServiceImpl<ZtRolePostInfo> implements IZtRolePostInfoService {

    @Override
    public String getTableName() {
        return "zt_role_post_info";
    }

    @Override
    public ZtParamEntity<ZtRolePostInfo> ztBeforeSimpleInsert(ZtParamEntity<ZtRolePostInfo> ztParamEntity) throws Exception {
        ZtRolePostInfo entity = ztParamEntity.getEntity();
        Object otherParams = entity.getOtherParams();
        ZtRolePostInfo tmp4Del = new ZtRolePostInfo();
        if (otherParams.equals(ZtMenuCodeEnum.ROLE_MANAGE.name())) {
            tmp4Del.setRoleCode(entity.getRoleCode());
        }
        ZtParamEntity<ZtRolePostInfo> ztParamEntity4Del = getThisService().getInitZtParamEntityWithOutCount(tmp4Del);
        ztParamEntity4Del = getThisService().ztSimpleSelectProviderWithoutCount(ztParamEntity4Del);
        List<ZtRolePostInfo> delList = getThisService().getList(ztParamEntity4Del);
        if (delList.size() > 0) {
            ztParamEntity4Del.setEntityList(delList);
            getThisService().ztSimpleDeleteByPrimaryKeyBatch(ztParamEntity4Del);
        }

        return super.ztBeforeSimpleInsert(ztParamEntity);
    }

    @Override
    public ZtParamEntity<ZtRolePostInfo> ztBeforeSimpleInsertBatch(ZtParamEntity<ZtRolePostInfo> ztParamEntity) throws Exception {
        List<ZtRolePostInfo> entityList = ztParamEntity.getEntityList();
        if (entityList.size() > 0) {
            Object otherParams = entityList.get(0).getOtherParams();
            ZtRolePostInfo tmp4Del = new ZtRolePostInfo();
            ZtParamEntity<ZtRolePostInfo> ztParamEntity4Del = getThisService().getInitZtParamEntityWithOutCount(tmp4Del);
            if (otherParams.equals(ZtMenuCodeEnum.ROLE_MANAGE.name())) {
                List<String> roleCodes = entityList.stream().map(ZtRolePostInfo::getRoleCode).distinct().collect(Collectors.toList());
                ztParamEntity4Del.andIn(ZtRolePostInfo::getRoleCode, roleCodes);
            }
            ztParamEntity4Del = getThisService().ztSimpleSelectProviderWithoutCount(ztParamEntity4Del);
            List<ZtRolePostInfo> delList = getThisService().getList(ztParamEntity4Del);
            if (delList.size() > 0) {
                ztParamEntity4Del.setEntityList(delList);
                getThisService().ztSimpleDeleteByPrimaryKeyBatch(ztParamEntity4Del);
            }
        }

        return super.ztBeforeSimpleInsertBatch(ztParamEntity);
    }
}

