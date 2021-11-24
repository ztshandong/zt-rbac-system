package com.zhangzhuorui.framework.rbacsystem.controller;

import com.zhangzhuorui.framework.core.ZtPage;
import com.zhangzhuorui.framework.core.ZtResBeanEx;
import com.zhangzhuorui.framework.core.ZtStrUtils;
import com.zhangzhuorui.framework.rbacsystem.aspects.ZtPreAuthorize;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtDeptInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtMenuCodeEnum;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseController;
import com.zhangzhuorui.framework.rbacsystem.service.IZtDeptInfoService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 基于张涛极速开发框架的RBAC权限管理系统
 *
 * @author zhangtao
 * @version :  1.0
 * @createDate : 2017-01-01
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@Api(tags = "部门", description = "ZtDeptInfoController")
@RestController
@RequestMapping(value = "/ZtDeptInfo")
@ZtPreAuthorize(hasAnyPermi = {ZtMenuCodeEnum.DEPT_MANAGE_CODE})
public class ZtDeptInfoController extends ZtRbacSimpleBaseController<ZtDeptInfo> {

    @Override
    protected IZtDeptInfoService getIZtSimpleBaseService() {
        return (IZtDeptInfoService) super.getIZtSimpleBaseService();
    }

    @Override
    @RequestMapping(value = ZtStrUtils.SELECT_SIMPLE_ALL, method = RequestMethod.POST)
    @ResponseBody
    public ZtResBeanEx<ZtPage<ZtDeptInfo>> ztSimpleSelectAll() throws Exception {
        return super.ztSimpleSelectAll();
    }

    @RequestMapping(value = "getAllDeptTree", method = RequestMethod.POST)
    @ResponseBody
    public ZtResBeanEx<ZtPage<ZtDeptInfo>> getAllDeptTree() throws Exception {
        List<ZtDeptInfo> allDeptTree = getIZtSimpleBaseService().getAllDeptTree();
        ZtPage ztPage = new ZtPage();
        ztPage.setResults(allDeptTree);
        ztPage.setTotal(allDeptTree.size());
        return ZtResBeanEx.ok(ztPage);
    }

    @RequestMapping(value = "getCurUserDeptCodes", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getCurUserDeptCodes() {
        ZtUserInfo userInfoFromToken = getIZtSimpleBaseService().getSimpleUserInfoFromToken();
        List<String> curUserDeptCodes = getIZtSimpleBaseService().getCurUserDeptCodes(userInfoFromToken);
        return curUserDeptCodes;
    }

}

