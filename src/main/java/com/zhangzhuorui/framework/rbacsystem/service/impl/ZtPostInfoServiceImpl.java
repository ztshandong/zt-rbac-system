package com.zhangzhuorui.framework.rbacsystem.service.impl;

import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheManager;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheUtil;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtExcludeInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtPostInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserPostInfo;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtRoleStatusEnum;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseServiceImpl;
import com.zhangzhuorui.framework.rbacsystem.service.IZtExcludeInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtPostInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtUserPostInfoService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class ZtPostInfoServiceImpl extends ZtRbacSimpleBaseServiceImpl<ZtPostInfo> implements IZtPostInfoService {

    @Override
    public String getTableName() {
        return "zt_post_info";
    }

    @Autowired
    ZtCacheUtil ztCacheUtil;

    @Autowired
    IZtExcludeInfoService iZtExcludeInfoService;

    @Autowired
    IZtUserPostInfoService iZtUserPostInfoService;

    @Override
    public void refreshCache() throws Exception {
        ztCacheUtil.evictCaffeine(ZtCacheUtil.ALL_POST_INFO);
        ztCacheUtil.evictCaffeine(ZtCacheUtil.CUR_USER_POST_CODES + "*");
    }

    @Override
    @Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, keyGenerator = ZtCacheUtil.ALL_POST_INFO + ZtCacheUtil.KEY_GENERATOR)
    public ZtParamEntity<ZtPostInfo> ztSimpleSelectAll() throws Exception {
        ZtPostInfo ztPostInfo = new ZtPostInfo();
        ztPostInfo.setStart(0L);
        ztPostInfo.setLimit(99999L);
        ZtParamEntity<ZtPostInfo> ztPostInfoParamEntity = getThisService().getInitZtParamEntityWithOutCount(ztPostInfo);
        ztPostInfoParamEntity = getThisService().ztSimpleSelectProviderWithoutCount(ztPostInfoParamEntity);
        return ztPostInfoParamEntity;
    }

    /**
     * 获取用户所有职位
     * 1.查询用户职位关联表，排除特定职位
     * 2.查询子职位，排除特定职位
     */
    @Override
    @SneakyThrows
    @Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.CUR_USER_POST_CODES + "+#userInfo.userCode")
    public List<String> getCurUserPostCodes(ZtUserInfo userInfo) {
        String userCode = userInfo.getUserCode();

        //用户所属职位
        ZtUserPostInfo ztUserPostInfo = new ZtUserPostInfo();
        ztUserPostInfo.setUserCode(userCode);
        List<ZtUserPostInfo> ztUserPostInfoList = iZtUserPostInfoService.ztSimpleGetList(ztUserPostInfo);
        List<String> ztCurUserPostCodes_1 = ztUserPostInfoList.stream().map(ZtUserPostInfo::getPostCode).collect(Collectors.toList());

        //排除的职位
        ZtExcludeInfo ztExcludeInfo = new ZtExcludeInfo();
        ztExcludeInfo.setUserCode(userCode);
        ztExcludeInfo.setExcludeType(ZtRoleStatusEnum.POST);
        List<ZtExcludeInfo> ztExcludePostList = iZtExcludeInfoService.ztSimpleGetList(ztExcludeInfo);
        List<String> ztExcludePostCodes = ztExcludePostList.stream().map(ZtExcludeInfo::getExcludeCode).collect(Collectors.toList());

        ztCurUserPostCodes_1.removeAll(ztExcludePostCodes);

        ZtParamEntity<ZtPostInfo> ztPostInfoZtParamEntity = getThisService().ztSimpleSelectAll();
        //所有的职位
        List<ZtPostInfo> allZtPostList = getThisService().getList(ztPostInfoZtParamEntity);

        List<ZtPostInfo> ztPostInfoList_1 = allZtPostList.stream().filter(t -> ztCurUserPostCodes_1.contains(t.getThisCode())).collect(Collectors.toList());

        //查找当前职位的子职位
        Set<ZtPostInfo> curUserAllPostSet = new HashSet<>(ztPostInfoList_1);
        for (ZtPostInfo parentPost : ztPostInfoList_1) {
            getChildPosts(parentPost, allZtPostList, curUserAllPostSet);
        }

        //仍然还要再筛一遍
        curUserAllPostSet = curUserAllPostSet.stream().filter(t -> !ztExcludePostCodes.contains(t.getThisCode())).collect(Collectors.toSet());

        //用户所属的最终的职位
        List<ZtPostInfo> curUserDeptInfoList = new ArrayList<>(curUserAllPostSet);

        List<String> curUserPostCodes = curUserDeptInfoList.stream().map(ZtPostInfo::getThisCode).collect(Collectors.toList());
        return curUserPostCodes;
    }

    public void getChildPosts(ZtPostInfo parentPost, List<ZtPostInfo> allZtPostList, Set<ZtPostInfo> curUserAllPostSet) {
        List<ZtPostInfo> childs = allZtPostList.stream().filter(t -> t.getParentCode().equalsIgnoreCase(parentPost.getThisCode())).collect(Collectors.toList());
        if (childs.size() > 0) {
            curUserAllPostSet.addAll(childs);
            for (ZtPostInfo child : childs) {
                getChildPosts(child, allZtPostList, curUserAllPostSet);
            }
        }
    }

}

