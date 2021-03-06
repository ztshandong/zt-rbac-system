package com.zhangzhuorui.framework.rbacsystem.service.impl;

import com.alibaba.fastjson.JSON;
import com.zhangzhuorui.framework.core.ZtQueryTypeEnum;
import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheManager;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheUtil;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtComponentInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtExcludeInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtRoleDeptInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtRoleInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtRolePostInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtRoleUserInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserPostInfo;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtComponentTypeEnum;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtDataScopeTypeEnum;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtRoleStatusEnum;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtRoleTypeEnum;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseServiceImpl;
import com.zhangzhuorui.framework.rbacsystem.service.IZtComponentInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtDeptInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtExcludeInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtPostInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleComponentInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleDeptInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRolePostInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleUserInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtUserInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtUserPostInfoService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ?????????????????????????????????RBAC?????????????????? ???????????????
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
public class ZtRoleInfoServiceImpl extends ZtRbacSimpleBaseServiceImpl<ZtRoleInfo> implements IZtRoleInfoService {

    @Override
    public String getTableName() {
        return "zt_role_info";
    }

    @Override
    protected IZtRoleInfoService getThisService() {
        return (IZtRoleInfoService) super.getThisService();
    }

    @Autowired
    IZtExcludeInfoService iZtExcludeInfoService;

    @Autowired
    IZtUserInfoService iZtUserInfoService;

    @Autowired
    IZtDeptInfoService iZtDeptInfoService;

    @Autowired
    IZtPostInfoService iZtPostInfoService;

    @Autowired
    IZtComponentInfoService iZtComponentInfoService;

    @Autowired
    IZtUserPostInfoService iZtUserPostInfoService;

    @Autowired
    IZtRoleDeptInfoService iZtRoleDeptInfoService;

    @Autowired
    IZtRolePostInfoService iZtRolePostInfoService;

    @Autowired
    IZtRoleUserInfoService iZtRoleUserInfoService;

    @Autowired
    IZtRoleComponentInfoService iZtRoleComponentInfoService;

    @Override
    @Caching(evict =
            {
                    @CacheEvict(cacheNames = ZtCacheUtil.ALL_ROLE_INFO, allEntries = true, cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
                    , @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_ROLE_CODES, allEntries = true, cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
                    , @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_DATA_ROLE_AND_DEPT_CODES, allEntries = true, cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
                    , @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_DATA_ROLE_OR_DEPT_CODES, allEntries = true, cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
                    , @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_DATA_ROLE_AND_USER_CODES, allEntries = true, cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
                    , @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_DATA_ROLE_OR_USER_CODES, allEntries = true, cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
                    , @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_ROUTER, allEntries = true, cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
                    , @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_PERMISSION, allEntries = true, cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
                    , @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_ADMIN_FLAG, allEntries = true, cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
            }
    )
    public void refreshCache() throws Exception {

    }

    @Override
    @Caching(evict =
            {
                    @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_ROLE_CODES, key = "#userId", cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
                    , @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_DATA_ROLE_AND_DEPT_CODES, key = "#userId", cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
                    , @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_DATA_ROLE_OR_DEPT_CODES, key = "#userId", cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
                    , @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_DATA_ROLE_AND_USER_CODES, key = "#userId", cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
                    , @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_DATA_ROLE_OR_USER_CODES, key = "#userId", cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
                    , @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_ROUTER, key = "#userId", cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
                    , @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_PERMISSION, key = "#userId", cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
                    , @CacheEvict(cacheNames = ZtCacheUtil.CUR_USER_ADMIN_FLAG, key = "#userId", cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)
            }
    )
    public void refreshCacheByCurUserId(Long userId) {

    }

    @Override
    @Caching(cacheable =
            {@Cacheable(cacheNames = ZtCacheUtil.ALL_ROLE_INFO, keyGenerator = ZtCacheUtil.ALL_ROLE_INFO + ZtCacheUtil.KEY_GENERATOR, cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)}
    )
    // @Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, keyGenerator = ZtCacheUtil.ALL_ROLE_INFO + ZtCacheUtil.KEY_GENERATOR)
    public ZtParamEntity<ZtRoleInfo> ztSimpleSelectAll() throws Exception {
        ZtRoleInfo ztRoleInfo = new ZtRoleInfo();
        ztRoleInfo.setStart(0L);
        ztRoleInfo.setLimit(99999L);
        ZtParamEntity<ZtRoleInfo> ztRoleInfoParamEntity = getThisService().getInitZtParamEntityWithOutCount(ztRoleInfo);
        ztRoleInfoParamEntity = getThisService().ztSimpleSelectProviderWithoutCount(ztRoleInfoParamEntity);
        return ztRoleInfoParamEntity;
    }

    /**
     * ????????????????????????
     *
     * @param userInfo :
     * @return :  java.util.List<com.zhangzhuorui.framework.rbacsystem.entity.ZtComponentInfo>
     * @author :  zhangtao
     * @createDate :  2021/10/5 ??????3:29
     * @description :
     * @updateUser :
     * @updateDate :
     * @updateRemark :
     */
    @Override
    @SneakyThrows
    @Caching(cacheable =
            {@Cacheable(cacheNames = ZtCacheUtil.CUR_USER_ROUTER, key = "#userInfo.id", cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)}
            // {@Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.CUR_USER_ROUTER + "+#userInfo.id")}
    )
    public List<ZtComponentInfo> getCurUserRouteAfterLogin(ZtUserInfo userInfo) {
        ZtParamEntity<ZtComponentInfo> ztComponentInfoZtParamEntity = iZtComponentInfoService.ztSimpleSelectAll();
        List<ZtComponentInfo> allComponent = iZtComponentInfoService.getList(ztComponentInfoZtParamEntity);
        List<ZtComponentInfo> curUserAllComponentListWithoutButton = allComponent.stream().filter(t -> !ZtComponentTypeEnum.BUTTON.equals(t.getMenuType())).collect(Collectors.toList());
        Boolean curUserAdminFlag = getThisService().getCurUserAdminFlag(userInfo);
        if (!curUserAdminFlag) {
            List<String> curUserAllComponentCodes = iZtComponentInfoService.getCurUserAllComponentCodes(userInfo);
            curUserAllComponentListWithoutButton = curUserAllComponentListWithoutButton.stream().filter(t -> curUserAllComponentCodes.contains(t.getThisCode())).collect(Collectors.toList());
        }
        curUserAllComponentListWithoutButton = JSON.parseArray(JSON.toJSONString(curUserAllComponentListWithoutButton), ZtComponentInfo.class);
        Collections.sort(curUserAllComponentListWithoutButton);
        for (ZtComponentInfo ztComponentInfo : curUserAllComponentListWithoutButton) {
            buildComponentTree(ztComponentInfo, curUserAllComponentListWithoutButton);
        }
        curUserAllComponentListWithoutButton = curUserAllComponentListWithoutButton.stream().filter(t -> ZtComponentTypeEnum.MODEL.equals(t.getMenuType())).collect(Collectors.toList());

        return curUserAllComponentListWithoutButton;
    }

    @Override
    @SneakyThrows
    @Caching(cacheable =
            {@Cacheable(cacheNames = ZtCacheUtil.CUR_USER_PERMISSION, key = "#userInfo.id", cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)}
            // {@Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.CUR_USER_PERMISSION + "+#userInfo.id")}
    )
    public List<String> getCurUserPermission(ZtUserInfo userInfo) {
        ZtParamEntity<ZtComponentInfo> ztComponentInfoZtParamEntity = iZtComponentInfoService.ztSimpleSelectAll();
        List<ZtComponentInfo> allComponent = iZtComponentInfoService.getList(ztComponentInfoZtParamEntity);
        List<String> curUserLeafComponentCodes = iZtComponentInfoService.getCurUserLeafComponentCodes(userInfo);
        List<ZtComponentInfo> curUserAllButtonComponent = allComponent.stream().filter(t -> ZtComponentTypeEnum.BUTTON.equals(t.getMenuType()) && curUserLeafComponentCodes.contains(t.getThisCode())).collect(Collectors.toList());
        List<String> curUserPermission = curUserAllButtonComponent.stream().map(t -> {
            return t.getThisCode();
        }).collect(Collectors.toList());
        return curUserPermission;
    }

    /**
     * ????????????????????????
     * ??????????????????????????????
     * <p>
     * 1.getCurUserDeptCodes
     * 2.???????????????????????????????????????
     * 3.getCurUserPostCodes
     * 4.???????????????????????????????????????
     * 5.???????????????????????????????????????
     * 6.?????????????????????????????????????????????????????????????????????
     * 7.?????????????????????????????????????????????????????????
     * ?????????????????????????????????
     */
    @Override
    @SneakyThrows
    @Caching(cacheable =
            {@Cacheable(cacheNames = ZtCacheUtil.CUR_USER_ROLE_CODES, key = "#userInfo.id", cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)}
    )
    // @Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.CUR_USER_ROLE_CODES + "+#userInfo.id")
    public List<String> getCurUserAllRoleCodes(ZtUserInfo userInfo) {
        String userCode = userInfo.getUserCode();

        //??????????????????
        Set<String> curUserAllRoleCodeSet = new HashSet<>();

        //???????????????
        ZtExcludeInfo ztExcludeInfo = new ZtExcludeInfo();
        ztExcludeInfo.setUserCode(userCode);
        ztExcludeInfo.setExcludeType(ZtRoleStatusEnum.ROLE);
        List<ZtExcludeInfo> ztExcludeRoleList = iZtExcludeInfoService.ztSimpleGetList(ztExcludeInfo);
        List<String> ztExcludeRoleCodes = ztExcludeRoleList.stream().map(ZtExcludeInfo::getExcludeCode).distinct().collect(Collectors.toList());

        //1 ??????
        List<String> curUserDeptCodes = iZtDeptInfoService.getCurUserDeptCodes(userInfo);
        if (curUserDeptCodes.size() > 0) {
            ZtRoleDeptInfo ztRoleDeptInfo = new ZtRoleDeptInfo();
            ZtParamEntity<ZtRoleDeptInfo> ztRoleDeptInfoParamEntity = iZtRoleDeptInfoService.getInitZtParamEntityWithOutCount(ztRoleDeptInfo);
            ztRoleDeptInfoParamEntity.andIn(ZtRoleDeptInfo::getDeptCode, curUserDeptCodes);
            ztRoleDeptInfoParamEntity = iZtRoleDeptInfoService.ztSimpleSelectProvider(ztRoleDeptInfoParamEntity);
            //2
            List<ZtRoleDeptInfo> ztRoleDeptInfoList = iZtRoleDeptInfoService.getList(ztRoleDeptInfoParamEntity);
            List<String> deptRoleCodes = ztRoleDeptInfoList.stream().map(ZtRoleDeptInfo::getRoleCode).distinct().collect(Collectors.toList());
            deptRoleCodes.removeAll(ztExcludeRoleCodes);
            curUserAllRoleCodeSet.addAll(deptRoleCodes);
        }

        //3 ??????
        List<String> curUserPostCodes = iZtPostInfoService.getCurUserPostCodes(userInfo);
        if (curUserPostCodes.size() > 0) {
            ZtRolePostInfo ztRolePostInfo = new ZtRolePostInfo();
            ZtParamEntity<ZtRolePostInfo> ztRolePostInfoParamEntity = iZtRolePostInfoService.getInitZtParamEntityWithOutCount(ztRolePostInfo);
            ztRolePostInfoParamEntity.andIn(ZtRolePostInfo::getPostCode, curUserPostCodes);
            ztRolePostInfoParamEntity = iZtRolePostInfoService.ztSimpleSelectProvider(ztRolePostInfoParamEntity);
            //4
            List<ZtRolePostInfo> ztRolePostInfoList = iZtRolePostInfoService.getList(ztRolePostInfoParamEntity);
            List<String> postRoleCodes = ztRolePostInfoList.stream().map(ZtRolePostInfo::getRoleCode).distinct().collect(Collectors.toList());
            postRoleCodes.removeAll(ztExcludeRoleCodes);
            curUserAllRoleCodeSet.addAll(postRoleCodes);
        }

        //5 ??????
        ZtRoleUserInfo ztRoleUserInfo = new ZtRoleUserInfo();
        ztRoleUserInfo.setUserCode(userCode);
        List<ZtRoleUserInfo> ztRoleUserInfos = iZtRoleUserInfoService.ztSimpleGetList(ztRoleUserInfo);
        List<String> userRoleCodes = ztRoleUserInfos.stream().map(ZtRoleUserInfo::getRoleCode).distinct().collect(Collectors.toList());
        userRoleCodes.removeAll(ztExcludeRoleCodes);
        curUserAllRoleCodeSet.addAll(userRoleCodes);

        //6
        curUserAllRoleCodeSet.removeAll(ztExcludeRoleCodes);

        ZtParamEntity<ZtRoleInfo> ztRoleInfoZtParamEntity = getThisService().ztSimpleSelectAll();
        //???????????????
        List<ZtRoleInfo> allZtRoleList = getThisService().getList(ztRoleInfoZtParamEntity);
        //??????????????????
        List<ZtRoleInfo> ztRoleInfoList_1 = allZtRoleList.stream().filter(t -> curUserAllRoleCodeSet.contains(t.getThisCode())).distinct().collect(Collectors.toList());

        //7
        for (ZtRoleInfo parentRole : ztRoleInfoList_1) {
            getChildRoles(parentRole, allZtRoleList, curUserAllRoleCodeSet);
        }

        //????????????????????????
        curUserAllRoleCodeSet.removeAll(ztExcludeRoleCodes);

        //?????????????????????
        List<String> curUserRoleCodes = new ArrayList<>(curUserAllRoleCodeSet);
        return curUserRoleCodes;
    }

    public void getChildRoles(ZtRoleInfo parentRole, List<ZtRoleInfo> allZtRoleList, Set<String> curUserAllRoleCodeSet) {
        List<ZtRoleInfo> childs = allZtRoleList.stream().filter(t -> t.getParentCode().equalsIgnoreCase(parentRole.getThisCode())).collect(Collectors.toList());
        if (childs.size() > 0) {
            List<String> collect = childs.stream().map(ZtRoleInfo::getThisCode).collect(Collectors.toList());
            curUserAllRoleCodeSet.addAll(collect);
            for (ZtRoleInfo child : childs) {
                getChildRoles(child, allZtRoleList, curUserAllRoleCodeSet);
            }
        }
    }

    /**
     * ???????????????dataScopeDeptFlag??????
     * ????????????????????????-???????????????????????????????????? SQL ????????? AND
     * 1.getCurUserAllRoleCodes
     * 2.?????????????????????????????????
     * 3.ZtQueryTypeEnum???AND???DEPT_CUSTOM???getCurUserDeptCodes?????????
     */
    @Override
    @SneakyThrows
    @Caching(cacheable =
            {@Cacheable(cacheNames = ZtCacheUtil.CUR_USER_DATA_ROLE_AND_DEPT_CODES, key = "#userInfo.id", cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)}
    )
    // @Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.CUR_USER_DATA_ROLE_AND_DEPT_CODES + "+#userInfo.id")
    public List<String> getCurUserDataRoleAndDeptCodes(ZtUserInfo userInfo) {
        //1
        List<String> curUserAllRoleCodes = getThisService().getCurUserAllRoleCodes(userInfo);
        ZtParamEntity<ZtRoleInfo> ztRoleInfoZtParamEntity = getThisService().ztSimpleSelectAll();
        List<ZtRoleInfo> allZtRoleList = getThisService().getList(ztRoleInfoZtParamEntity);

        List<String> curUserDeptCodes = iZtDeptInfoService.getCurUserDeptCodes(userInfo);
        Set<String> curUserDeptCodeSet = new HashSet<>(curUserDeptCodes);

        //2
        List<ZtRoleInfo> curUserDeptCustomAndList = allZtRoleList.stream().filter(t -> {
            return ZtQueryTypeEnum.AND.equals(t.getDataScopeOptType())
                    && ZtDataScopeTypeEnum.DEPT_CUSTOM.equals(t.getDataScopeType())
                    && !ZtRoleTypeEnum.COMPONENT.equals(t.getRoleType())
                    && curUserAllRoleCodes.contains(t.getThisCode())
                    ;
        }).collect(Collectors.toList());
        //3
        for (ZtRoleInfo ztRoleInfo : curUserDeptCustomAndList) {
            curUserDeptCodeSet.retainAll(ztRoleInfo.getRoleCustom());
        }

        List<String> curUserDataRoleDeptCodes = new ArrayList<>(curUserDeptCodeSet);
        return curUserDataRoleDeptCodes;
    }

    /**
     * ???????????????dataScopeDeptFlag??????
     * ????????????????????????-???????????????????????????????????? SQL ????????? OR
     * 1.getCurUserAllRoleCodes
     * 2.?????????????????????????????????
     * 3.getCurUserDeptCodes??????ZtQueryTypeEnum???OR???DEPT_CUSTOM
     */
    @Override
    @SneakyThrows
    @Caching(cacheable =
            {@Cacheable(cacheNames = ZtCacheUtil.CUR_USER_DATA_ROLE_OR_DEPT_CODES, key = "#userInfo.id", cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)}
    )
    // @Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.CUR_USER_DATA_ROLE_OR_DEPT_CODES + "+#userInfo.id")
    public List<String> getCurUserDataRoleOrDeptCodes(ZtUserInfo userInfo) {
        //1
        List<String> curUserAllRoleCodes = getThisService().getCurUserAllRoleCodes(userInfo);
        ZtParamEntity<ZtRoleInfo> ztRoleInfoZtParamEntity = getThisService().ztSimpleSelectAll();
        List<ZtRoleInfo> allZtRoleList = getThisService().getList(ztRoleInfoZtParamEntity);

        List<String> curUserDeptCodes = iZtDeptInfoService.getCurUserDeptCodes(userInfo);
        Set<String> curUserDeptCodeSet = new HashSet<>(curUserDeptCodes);

        //2
        List<ZtRoleInfo> curUserDeptCustomOrList = allZtRoleList.stream().filter(t -> {
            return ZtQueryTypeEnum.OR.equals(t.getDataScopeOptType())
                    && ZtDataScopeTypeEnum.DEPT_CUSTOM.equals(t.getDataScopeType())
                    && !ZtRoleTypeEnum.COMPONENT.equals(t.getRoleType())
                    && curUserAllRoleCodes.contains(t.getThisCode())
                    ;
        }).collect(Collectors.toList());
        //3
        for (ZtRoleInfo ztRoleInfo : curUserDeptCustomOrList) {
            curUserDeptCodeSet.addAll(ztRoleInfo.getRoleCustom());
        }
        List<String> curUserDataRoleDeptCodes = new ArrayList<>(curUserDeptCodeSet);
        return curUserDataRoleDeptCodes;
    }

    /**
     * ???????????????dataScopeUserFlag??????
     * ????????????????????????-???????????????????????????????????? SQL ????????? AND
     * ??????????????????????????????
     * 1.getCurUserAllRoleCodes ??????????????????????????????????????????????????????
     * 2.getCurUserPostCodes ????????????????????????
     * 3.ZtQueryTypeEnum???AND???POST_CUSTOM???getCurUserPostCodes?????????
     * 4.??????????????????????????? curUserDataRoleUserCodeSet
     * 5.ZtQueryTypeEnum???AND???USER_CUSTOM???curUserDataRoleUserCodeSet?????????
     */
    @Override
    @SneakyThrows
    @Caching(cacheable =
            {@Cacheable(cacheNames = ZtCacheUtil.CUR_USER_DATA_ROLE_AND_USER_CODES, key = "#userInfo.id", cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)}
    )
    // @Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.CUR_USER_DATA_ROLE_AND_USER_CODES + "+#userInfo.id")
    public List<String> getCurUserDataRoleAndUserCodes(ZtUserInfo userInfo) {

        List<String> curUserAllRoleCodes = getThisService().getCurUserAllRoleCodes(userInfo);
        ZtParamEntity<ZtRoleInfo> ztRoleInfoZtParamEntity = getThisService().ztSimpleSelectAll();
        List<ZtRoleInfo> curUserAllRoleList = getThisService().getList(ztRoleInfoZtParamEntity);
        //1
        curUserAllRoleList = curUserAllRoleList.stream().filter(t -> !ZtRoleTypeEnum.COMPONENT.equals(t.getRoleType()) && curUserAllRoleCodes.contains(t.getThisCode())).collect(Collectors.toList());

        //2
        List<String> curUserPostCodes = iZtPostInfoService.getCurUserPostCodes(userInfo);
        Set<String> curUserPostCodeSet = new HashSet<>(curUserPostCodes);

        //3
        List<ZtRoleInfo> curUserPostCustomAndList = curUserAllRoleList.stream().filter(t -> {
            return ZtQueryTypeEnum.AND.equals(t.getDataScopeOptType())
                    && ZtDataScopeTypeEnum.POST_CUSTOM.equals(t.getDataScopeType())
                    ;
        }).collect(Collectors.toList());
        for (ZtRoleInfo ztRoleInfo : curUserPostCustomAndList) {
            curUserPostCodeSet.retainAll(ztRoleInfo.getRoleCustom());
        }

        //4
        ZtUserPostInfo ztUserPostInfo = new ZtUserPostInfo();
        ZtParamEntity<ZtUserPostInfo> ztUserPostInfoZtParamEntity = iZtUserPostInfoService.getInitZtParamEntityWithOutCount(ztUserPostInfo);
        ztUserPostInfoZtParamEntity.andIn(ZtUserPostInfo::getPostCode, new ArrayList(curUserPostCodeSet));
        ztUserPostInfoZtParamEntity = iZtUserPostInfoService.ztSimpleSelectProviderWithoutCount(ztUserPostInfoZtParamEntity);
        List<ZtUserPostInfo> ztUserPostInfoList = iZtUserPostInfoService.getList(ztUserPostInfoZtParamEntity);
        List<String> userCodes = ztUserPostInfoList.stream().map(ZtUserPostInfo::getUserCode).collect(Collectors.toList());

        Set<String> curUserDataRoleUserCodeSet = new HashSet<>(userCodes);

        //5
        List<ZtRoleInfo> curUserCustomAndList = curUserAllRoleList.stream().filter(t -> {
            return ZtQueryTypeEnum.AND.equals(t.getDataScopeOptType())
                    && ZtDataScopeTypeEnum.USER_CUSTOM.equals(t.getDataScopeType())
                    ;
        }).collect(Collectors.toList());
        for (ZtRoleInfo ztRoleInfo : curUserCustomAndList) {
            curUserDataRoleUserCodeSet.retainAll(ztRoleInfo.getRoleCustom());
        }
        curUserDataRoleUserCodeSet.add(userInfo.getUserCode());
        List<String> curUserDataRoleUserCodeList = new ArrayList<>(curUserDataRoleUserCodeSet);
        return curUserDataRoleUserCodeList;
    }

    /**
     * ???????????????dataScopeUserFlag??????
     * ????????????????????????-???????????????????????????????????? SQL ????????? OR
     * ??????????????????????????????
     * 1.getCurUserAllRoleCodes ??????????????????????????????????????????????????????
     * 2.getCurUserPostCodes ????????????????????????
     * 3.getCurUserPostCodes??????ZtQueryTypeEnum???OR???POST_CUSTOM
     * 4.??????????????????????????? curUserDataRoleUserCodeSet
     * 5.curUserDataRoleUserCodeSet??????ZtQueryTypeEnum???OR???USER_CUSTOM
     */
    @Override
    @SneakyThrows
    @Caching(cacheable =
            {@Cacheable(cacheNames = ZtCacheUtil.CUR_USER_DATA_ROLE_OR_USER_CODES, key = "#userInfo.id", cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)}
    )
    // @Cacheable(cacheNames = ZtCacheManager.CAFFEINE_CACHE, key = ZtCacheUtil.CUR_USER_DATA_ROLE_OR_USER_CODES + "+#userInfo.id")
    public List<String> getCurUserDataRoleOrUserCodes(ZtUserInfo userInfo) {

        List<String> curUserAllRoleCodes = getThisService().getCurUserAllRoleCodes(userInfo);
        ZtParamEntity<ZtRoleInfo> ztRoleInfoZtParamEntity = getThisService().ztSimpleSelectAll();
        List<ZtRoleInfo> curUserAllRoleList = getThisService().getList(ztRoleInfoZtParamEntity);
        //1
        curUserAllRoleList = curUserAllRoleList.stream().filter(t -> !ZtRoleTypeEnum.COMPONENT.equals(t.getRoleType()) && curUserAllRoleCodes.contains(t.getThisCode())).collect(Collectors.toList());

        //2
        List<String> curUserPostCodes = iZtPostInfoService.getCurUserPostCodes(userInfo);
        Set<String> curUserPostCodeSet = new HashSet<>(curUserPostCodes);

        //3
        List<ZtRoleInfo> curUserPostCustomAndList = curUserAllRoleList.stream().filter(t -> {
            return ZtQueryTypeEnum.OR.equals(t.getDataScopeOptType())
                    && ZtDataScopeTypeEnum.POST_CUSTOM.equals(t.getDataScopeType())
                    ;
        }).collect(Collectors.toList());
        for (ZtRoleInfo ztRoleInfo : curUserPostCustomAndList) {
            curUserPostCodeSet.addAll(ztRoleInfo.getRoleCustom());
        }

        //4
        ZtUserPostInfo ztUserPostInfo = new ZtUserPostInfo();
        ZtParamEntity<ZtUserPostInfo> ztUserPostInfoZtParamEntity = iZtUserPostInfoService.getInitZtParamEntityWithOutCount(ztUserPostInfo);
        ztUserPostInfoZtParamEntity.andIn(ZtUserPostInfo::getPostCode, new ArrayList(curUserPostCodeSet));
        ztUserPostInfoZtParamEntity = iZtUserPostInfoService.ztSimpleSelectProviderWithoutCount(ztUserPostInfoZtParamEntity);
        List<ZtUserPostInfo> ztUserPostInfoList = iZtUserPostInfoService.getList(ztUserPostInfoZtParamEntity);
        List<String> userCodes = ztUserPostInfoList.stream().map(ZtUserPostInfo::getUserCode).collect(Collectors.toList());

        Set<String> curUserDataRoleUserCodeSet = new HashSet<>(userCodes);

        //5
        List<ZtRoleInfo> curUserCustomAndList = curUserAllRoleList.stream().filter(t -> {
            return ZtQueryTypeEnum.OR.equals(t.getDataScopeOptType())
                    && ZtDataScopeTypeEnum.USER_CUSTOM.equals(t.getDataScopeType())
                    ;
        }).collect(Collectors.toList());
        for (ZtRoleInfo ztRoleInfo : curUserCustomAndList) {
            curUserDataRoleUserCodeSet.addAll(ztRoleInfo.getRoleCustom());
        }
        curUserDataRoleUserCodeSet.add(userInfo.getUserCode());
        List<String> curUserDataRoleUserCodeList = new ArrayList<>(curUserDataRoleUserCodeSet);
        return curUserDataRoleUserCodeList;
    }

    @Override
    @SneakyThrows
    @Caching(cacheable =
            {@Cacheable(cacheNames = ZtCacheUtil.CUR_USER_ADMIN_FLAG, key = "#userInfo.id", cacheManager = ZtCacheManager.CAFFEINE_CACHE_MANAGER)}
    )
    public Boolean getCurUserAdminFlag(ZtUserInfo userInfo) {
        userInfo = iZtUserInfoService.getFullUserInfoFromToken(userInfo);
        if (null == userInfo.getAdminFlag()) {
            return false;
        }
        if (userInfo.getAdminFlag()) {
            return true;
        }
        List<String> curUserAllRoleCodes = getThisService().getCurUserAllRoleCodes(userInfo);
        ZtParamEntity<ZtRoleInfo> ztRoleInfoZtParamEntity = getThisService().ztSimpleSelectAll();
        List<ZtRoleInfo> allRoleInfo = getThisService().getList(ztRoleInfoZtParamEntity);
        Optional<ZtRoleInfo> adminRole = allRoleInfo.stream().filter(t -> curUserAllRoleCodes.contains(t.getThisCode()) && t.getAdminFlag()).findAny();
        return adminRole.isPresent();
    }
}

