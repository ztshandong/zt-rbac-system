package com.zhangzhuorui.framework.rbacsystem.service.impl;

import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserDeptInfo;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtMenuCodeEnum;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseServiceImpl;
import com.zhangzhuorui.framework.rbacsystem.service.IZtUserDeptInfoService;
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
public class ZtUserDeptInfoServiceImpl extends ZtRbacSimpleBaseServiceImpl<ZtUserDeptInfo> implements IZtUserDeptInfoService {

    @Override
    public String getTableName() {
        return "zt_user_dept_info";
    }

    @Override
    public ZtParamEntity<ZtUserDeptInfo> ztBeforeSimpleInsert(ZtParamEntity<ZtUserDeptInfo> ztParamEntity) throws Exception {
        ZtUserDeptInfo entity = ztParamEntity.getEntity();
        Object otherParams = entity.getOtherParams();
        ZtUserDeptInfo tmp4Del = new ZtUserDeptInfo();
        if (otherParams.equals(ZtMenuCodeEnum.USER_MANAGE.name())) {
            tmp4Del.setUserCode(entity.getUserCode());
        } else if (otherParams.equals(ZtMenuCodeEnum.DEPT_MANAGE.name())) {
            tmp4Del.setDeptCode(entity.getDeptCode());
        }
        ZtParamEntity<ZtUserDeptInfo> ztUserDeptInfoZtParamEntity4Del = getThisService().getInitZtParamEntityWithOutCount(tmp4Del);
        ztUserDeptInfoZtParamEntity4Del = getThisService().ztSimpleSelectProviderWithoutCount(ztUserDeptInfoZtParamEntity4Del);
        List<ZtUserDeptInfo> delList = getThisService().getList(ztUserDeptInfoZtParamEntity4Del);
        if (delList.size() > 0) {
            ztUserDeptInfoZtParamEntity4Del.setEntityList(delList);
            getThisService().ztSimpleDeleteByPrimaryKeyBatch(ztUserDeptInfoZtParamEntity4Del);
        }

        return super.ztBeforeSimpleInsert(ztParamEntity);
    }

    @Override
    public ZtParamEntity<ZtUserDeptInfo> ztBeforeSimpleInsertBatch(ZtParamEntity<ZtUserDeptInfo> ztParamEntity) throws Exception {

        List<ZtUserDeptInfo> entityList = ztParamEntity.getEntityList();
        if (entityList.size() > 0) {
            Object otherParams = entityList.get(0).getOtherParams();
            ZtUserDeptInfo tmp4Del = new ZtUserDeptInfo();
            ZtParamEntity<ZtUserDeptInfo> ztUserDeptInfoZtParamEntity4Del = getThisService().getInitZtParamEntityWithOutCount(tmp4Del);
            if (otherParams.equals(ZtMenuCodeEnum.USER_MANAGE.name())) {
                List<String> userCodes = entityList.stream().map(ZtUserDeptInfo::getUserCode).distinct().collect(Collectors.toList());
                ztUserDeptInfoZtParamEntity4Del.andIn(ZtUserDeptInfo::getUserCode, userCodes);
            } else if (otherParams.equals(ZtMenuCodeEnum.DEPT_MANAGE.name())) {
                List<String> deptCodes = entityList.stream().map(ZtUserDeptInfo::getDeptCode).distinct().collect(Collectors.toList());
                ztUserDeptInfoZtParamEntity4Del.andIn(ZtUserDeptInfo::getDeptCode, deptCodes);
            }
            ztUserDeptInfoZtParamEntity4Del = getThisService().ztSimpleSelectProviderWithoutCount(ztUserDeptInfoZtParamEntity4Del);
            List<ZtUserDeptInfo> delList = getThisService().getList(ztUserDeptInfoZtParamEntity4Del);
            if (delList.size() > 0) {
                ztUserDeptInfoZtParamEntity4Del.setEntityList(delList);
                getThisService().ztSimpleDeleteByPrimaryKeyBatch(ztUserDeptInfoZtParamEntity4Del);
            }
        }
        return super.ztBeforeSimpleInsertBatch(ztParamEntity);
    }
}

