package com.zhangzhuorui.framework.rbacsystem.service.impl;

import com.zhangzhuorui.framework.mybatis.core.IZtSelectColumnService;
import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.mybatis.core.ZtQueryWrapper;
import com.zhangzhuorui.framework.mybatis.core.ZtSelectColumnHelper;
import com.zhangzhuorui.framework.rbacsystem.config.ZtJwtTokenUtil;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtSpecHide;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtSpecShow;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtRoleStatusEnum;
import com.zhangzhuorui.framework.rbacsystem.service.IZtDeptInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtPostInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtSpecHideService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtSpecShowService;
import lombok.SneakyThrows;
import org.apache.ibatis.mapping.ResultMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author :  张涛 zhangtao
 * @version :  1.0
 * @createDate :  2021/10/7 下午5:49
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@Service
public class ZtSelectColumnServiceImpl implements IZtSelectColumnService {

    @Autowired
    ZtJwtTokenUtil ztJwtTokenUtil;

    @Autowired
    IZtSpecHideService iZtSpecHideService;

    @Autowired
    IZtSpecShowService iZtSpecShowService;

    @Autowired
    IZtDeptInfoService iZtDeptInfoService;

    @Autowired
    IZtPostInfoService iZtPostInfoService;

    @Autowired
    IZtRoleInfoService iZtRoleInfoService;

    @Override
    @SneakyThrows
    public ZtSelectColumnHelper calCanSelect(ZtSelectColumnHelper ztSelectColumnHelper) {
        ResultMapping resultMapping = ztSelectColumnHelper.getResultMapping();
        ZtQueryWrapper qw = ztSelectColumnHelper.getQw();
        String fieldName = resultMapping.getProperty();
        String tableName = qw.getTableName();
        ZtSpecHide ztSpecHide = new ZtSpecHide();
        ztSpecHide.setVxeTableName(tableName);
        ztSpecHide.setVxeFieldName(fieldName);
        ZtUserInfo curUser = ztJwtTokenUtil.getSimpleUserInfoFromToken();
        List<String> curUserDeptCodes = iZtDeptInfoService.getCurUserDeptCodes(curUser);
        List<String> curUserPostCodes = iZtPostInfoService.getCurUserPostCodes(curUser);
        List<String> curUserAllRoleCodes = iZtRoleInfoService.getCurUserAllRoleCodes(curUser);

        ZtSpecShow ztSpecShow = new ZtSpecShow();
        ztSpecShow.setVxeTableName(tableName);
        ztSpecShow.setVxeFieldName(fieldName);

        //首先处理特殊显示字段
        ZtParamEntity<ZtSpecShow> ztSpecShowZtParamEntity = iZtSpecShowService.ztSimpleSelectAll();
        List<ZtSpecShow> ztSpecShows = iZtSpecShowService.getList(ztSpecShowZtParamEntity);
        ztSpecShows = ztSpecShows.stream().filter(t -> t.getVxeTableName().equals(tableName) && t.getVxeFieldName().equals(fieldName)).collect(Collectors.toList());
        for (ZtSpecShow specShow : ztSpecShows) {
            //如果ztSpecShow有值，说明这个字段对普通用户不可见，所以要首先设置为不可见，有权限的用户可见
            ztSelectColumnHelper.setCanSelect(false);
            if (specShow.getSpecType().equals(ZtRoleStatusEnum.DEPT)) {
                if (curUserDeptCodes.contains(specShow.getSpecCode())) {
                    ztSelectColumnHelper.setCanSelect(true);
                    break;
                }
            } else if (specShow.getSpecType().equals(ZtRoleStatusEnum.POST)) {
                if (curUserPostCodes.contains(specShow.getSpecCode())) {
                    ztSelectColumnHelper.setCanSelect(true);
                    break;
                }
            } else if (specShow.getSpecType().equals(ZtRoleStatusEnum.ROLE)) {
                if (curUserAllRoleCodes.contains(specShow.getSpecCode())) {
                    ztSelectColumnHelper.setCanSelect(true);
                    break;
                }
            } else if (specShow.getSpecType().equals(ZtRoleStatusEnum.USER)) {
                if (curUser.getUserCode().equals(specShow.getSpecCode())) {
                    ztSelectColumnHelper.setCanSelect(true);
                    break;
                }
            }
        }

        //最后处理隐藏字段，隐藏级别高
        ZtParamEntity<ZtSpecHide> ztSpecHideZtParamEntity = iZtSpecHideService.ztSimpleSelectAll();
        List<ZtSpecHide> ztSpecHides = iZtSpecHideService.getList(ztSpecHideZtParamEntity);
        ztSpecHides = ztSpecHides.stream().filter(t -> t.getVxeTableName().equals(tableName) && t.getVxeFieldName().equals(fieldName)).collect(Collectors.toList());
        for (ZtSpecHide specHide : ztSpecHides) {
            if (specHide.getSpecType().equals(ZtRoleStatusEnum.DEPT)) {
                if (curUserDeptCodes.contains(specHide.getSpecCode())) {
                    ztSelectColumnHelper.setCanSelect(false);
                    break;
                }
            } else if (specHide.getSpecType().equals(ZtRoleStatusEnum.POST)) {
                if (curUserPostCodes.contains(specHide.getSpecCode())) {
                    ztSelectColumnHelper.setCanSelect(false);
                    break;
                }
            } else if (specHide.getSpecType().equals(ZtRoleStatusEnum.ROLE)) {
                if (curUserAllRoleCodes.contains(specHide.getSpecCode())) {
                    ztSelectColumnHelper.setCanSelect(false);
                    break;
                }
            } else if (specHide.getSpecType().equals(ZtRoleStatusEnum.USER)) {
                if (curUser.getUserCode().equals(specHide.getSpecCode())) {
                    ztSelectColumnHelper.setCanSelect(false);
                    break;
                }
            }
        }
        return ztSelectColumnHelper;
    }
}
