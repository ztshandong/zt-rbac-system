package com.zhangzhuorui.framework.rbacsystem.service.impl;

import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheManager;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheUtil;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtDeptInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtExcludeInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserDeptInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtRoleStatusEnum;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseServiceImpl;
import com.zhangzhuorui.framework.rbacsystem.service.IZtDeptInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtExcludeInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtUserDeptInfoService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class ZtDeptInfoServiceImpl extends ZtRbacSimpleBaseServiceImpl<ZtDeptInfo> implements IZtDeptInfoService {

    @Override
    public String getTableName() {
        return "zt_dept_info";
    }

    @Autowired
    ZtCacheUtil ztCacheUtil;

    @Autowired
    IZtExcludeInfoService iZtExcludeInfoService;

    @Autowired
    IZtUserDeptInfoService iZtUserDeptInfoService;

    @Override
    public void refreshCache() throws Exception {
        ztCacheUtil.evictCaffeine(ZtCacheUtil.ALL_DEPT_INFO);
        ztCacheUtil.evictCaffeine(ZtCacheUtil.CUR_USER_DEPT_CODES + "*");
    }

    @Override
    @Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, keyGenerator = ZtCacheUtil.ALL_DEPT_INFO + ZtCacheUtil.KEY_GENERATOR)
    public ZtParamEntity<ZtDeptInfo> ztSimpleSelectAll() throws Exception {
        ZtDeptInfo ztDeptInfo = new ZtDeptInfo();
        ztDeptInfo.setStart(0L);
        ztDeptInfo.setLimit(99999L);
        ZtParamEntity<ZtDeptInfo> ztDeptInfoParamEntity = getThisService().getInitZtParamEntityWithOutCount(ztDeptInfo);
        ztDeptInfoParamEntity = getThisService().ztSimpleSelectProviderWithoutCount(ztDeptInfoParamEntity);
        return ztDeptInfoParamEntity;
    }

    /**
     * 获取用户所有部门
     * 1.查询用户部门关联表，排除特定部门
     * 2.查询子部门，排除特定部门
     */
    @Override
    @SneakyThrows
    @Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.CUR_USER_DEPT_CODES + "+#userInfo.userCode")
    public List<String> getCurUserDeptCodes(ZtUserInfo userInfo) {
        // ZtUserInfo userInfo = getUserInfoFromToken();
        String userCode = userInfo.getUserCode();

        //用户所属部门
        ZtUserDeptInfo ztUserDeptInfo = new ZtUserDeptInfo();
        ztUserDeptInfo.setUserCode(userCode);
        List<ZtUserDeptInfo> ztUserDeptInfos = iZtUserDeptInfoService.ztSimpleGetList(ztUserDeptInfo);
        List<String> ztCurUserDeptCodes_1 = ztUserDeptInfos.stream().map(ZtUserDeptInfo::getDeptCode).collect(Collectors.toList());

        //排除的部门
        ZtExcludeInfo ztExcludeInfo = new ZtExcludeInfo();
        ztExcludeInfo.setUserCode(userCode);
        ztExcludeInfo.setExcludeType(ZtRoleStatusEnum.DEPT);
        List<ZtExcludeInfo> ztExcludeDeptList = iZtExcludeInfoService.ztSimpleGetList(ztExcludeInfo);
        List<String> ztExcludeDeptCodes = ztExcludeDeptList.stream().map(ZtExcludeInfo::getExcludeCode).collect(Collectors.toList());

        ztCurUserDeptCodes_1.removeAll(ztExcludeDeptCodes);

        ZtParamEntity<ZtDeptInfo> ztDeptInfoZtParamEntity = getThisService().ztSimpleSelectAll();
        //所有的部门
        List<ZtDeptInfo> allZtDeptList = getThisService().getList(ztDeptInfoZtParamEntity);

        List<ZtDeptInfo> ztDeptInfoList_1 = allZtDeptList.stream().filter(t -> ztCurUserDeptCodes_1.contains(t.getThisCode())).collect(Collectors.toList());

        ztDeptInfoList_1.sort(Comparator.comparing(ZtDeptInfo::getDeptLevel));

        //查找当前部门的子部门
        Set<ZtDeptInfo> curUserAllDeptSet = new HashSet<>(ztDeptInfoList_1);
        for (ZtDeptInfo parentDept : ztDeptInfoList_1) {
            getChildDepts(parentDept, allZtDeptList, curUserAllDeptSet);
        }

        //仍然还要再筛一遍
        curUserAllDeptSet = curUserAllDeptSet.stream().filter(t -> !ztExcludeDeptCodes.contains(t.getThisCode())).collect(Collectors.toSet());

        //用户所属的最终的部门
        List<ZtDeptInfo> curUserDeptInfoList = new ArrayList<>(curUserAllDeptSet);

        List<String> curUserDeptCodes = curUserDeptInfoList.stream().map(ZtDeptInfo::getThisCode).collect(Collectors.toList());
        return curUserDeptCodes;
    }

    public void getChildDepts(ZtDeptInfo parentDept, List<ZtDeptInfo> allZtDeptList, Set<ZtDeptInfo> curUserAllDeptSet) {
        List<ZtDeptInfo> childs = allZtDeptList.stream().filter(t -> t.getParentCode().equalsIgnoreCase(parentDept.getThisCode())).collect(Collectors.toList());
        if (childs.size() > 0) {
            curUserAllDeptSet.addAll(childs);
            for (ZtDeptInfo child : childs) {
                getChildDepts(child, allZtDeptList, curUserAllDeptSet);
            }
        }
    }

}

