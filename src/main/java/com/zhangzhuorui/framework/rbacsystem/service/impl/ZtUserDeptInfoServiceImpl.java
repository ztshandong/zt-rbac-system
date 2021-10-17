package com.zhangzhuorui.framework.rbacsystem.service.impl;

import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserDeptInfo;
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

        ZtUserDeptInfo tmp4Del = new ZtUserDeptInfo();
        tmp4Del.setUserCode(entity.getUserCode());
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
        List<String> userCodes = entityList.stream().map(ZtUserDeptInfo::getUserCode).distinct().collect(Collectors.toList());

        ZtUserDeptInfo tmp4Del = new ZtUserDeptInfo();
        ZtParamEntity<ZtUserDeptInfo> ztUserDeptInfoZtParamEntity4Del = getThisService().getInitZtParamEntityWithOutCount(tmp4Del);
        ztUserDeptInfoZtParamEntity4Del.andIn(ZtUserDeptInfo::getUserCode, userCodes);
        ztUserDeptInfoZtParamEntity4Del = getThisService().ztSimpleSelectProviderWithoutCount(ztUserDeptInfoZtParamEntity4Del);
        List<ZtUserDeptInfo> delList = getThisService().getList(ztUserDeptInfoZtParamEntity4Del);
        if (delList.size() > 0) {
            ztUserDeptInfoZtParamEntity4Del.setEntityList(delList);
            getThisService().ztSimpleDeleteByPrimaryKeyBatch(ztUserDeptInfoZtParamEntity4Del);
        }

        return super.ztBeforeSimpleInsertBatch(ztParamEntity);
    }
}

