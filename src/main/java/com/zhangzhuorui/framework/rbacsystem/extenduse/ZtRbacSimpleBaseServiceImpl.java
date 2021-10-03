package com.zhangzhuorui.framework.rbacsystem.extenduse;

import com.zhangzhuorui.framework.core.ZtColumnUtil;
import com.zhangzhuorui.framework.core.ZtQueryTypeEnum;
import com.zhangzhuorui.framework.core.ZtUtils;
import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.mybatis.core.ZtQueryWrapper;
import com.zhangzhuorui.framework.mybatis.simplebaseservice.ZtSimpleBaseServiceImpl;
import com.zhangzhuorui.framework.rbacsystem.config.ZtJwtTokenUtil;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtRoleInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtDataScopeTypeEnum;
import lombok.SneakyThrows;
import org.apache.ibatis.mapping.SqlCommandType;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author :  张涛 zhangtao
 * @version :  1.0
 * @createDate :  2017-01-01
 * @description : 扩展使用
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
public abstract class ZtRbacSimpleBaseServiceImpl<T extends ZtRbacBasicEntity> extends ZtSimpleBaseServiceImpl<T> implements IZtRbacSimpleBaseService<T> {

    @Autowired
    ZtJwtTokenUtil ztJwtTokenUtil;

    @Override
    public ZtUserInfo getUserInfoFromToken() {
        String token = getRequest().getHeader(ztJwtTokenUtil.getTokenHeader());
        ZtUserInfo userInfoFromToken = ztJwtTokenUtil.getUserInfoFromToken(token);
        return userInfoFromToken;
    }

    /**
     * 数据权限 判断 ZtDataScopeTypeEnum 与 ZtQueryTypeEnum
     * 超级管理员有所有权限
     * <p>
     * 1.ZtDataScopeTypeEnum是SELF，ZtQueryTypeEnum是AND，忽略其他条件，只能看自己（最高优先级）
     * 不是情况1:
     * 2.ZtDataScopeTypeEnum是ALL，不论ZtQueryTypeEnum是什么，可以看所有人（次高优先级）
     * 不是情况1和情况2:
     * dataScopeDeptFlag()
     * 3.对ZtQueryTypeEnum是AND的DEPT_ALL与DEPT_CUSTOM取交集，获得严格匹配的部门
     * 4.添加ZtQueryTypeEnum是OR的DEPT_ALL与DEPT_CUSTOM，获得最终的部门
     * dataScopeUserFlag()
     * 5.对ZtQueryTypeEnum是AND的POST_ALL与POST_CUSTOM取交集，获得严格的职位
     * 6.添加ZtQueryTypeEnum是OR的POST_ALL与POST_CUSTOM，获得最终的职位
     * 7.根据职位查询出对应用户，与ZtQueryTypeEnum是AND的USER_CUSTOM取交集，获得严格的用户
     * 8.添加ZtQueryTypeEnum是OR的USER_CUSTOM，获得最终的用户
     * 9.添加缓存
     */
    @SneakyThrows
    @Override
    public ZtParamEntity<T> afterUseCommonZtQueryWrapper(ZtParamEntity<T> ztParamEntity, SqlCommandType sqlCommandType) {
        if (sqlCommandType.equals(SqlCommandType.SELECT)) {
            ZtUserInfo userInfo = (ZtUserInfo) ztParamEntity.getUserInfo();
            T entity = ztParamEntity.getEntity();
            ZtQueryWrapper<T> ztQueryWrapper = ztParamEntity.getZtQueryWrapper();
            if (dataScopeDeptFlag() || dataScopeUserFlag()) {
                //TODO getRole
                List<ZtRoleInfo> ztRoleInfoList = new ArrayList<>();
                // ZtRoleInfo ztRoleInfo = new ZtRoleInfo();
                // ztRoleInfo.setDataScopeOptType(ZtQueryTypeEnum.AND);
                // ztRoleInfo.setDataScopeType(ZtDataScopeTypeEnum.SELF);
                // ztRoleInfoList.add(ztRoleInfo);
                Optional<ZtRoleInfo> 情况1 = ztRoleInfoList.stream().filter(t -> {
                    return t.getDataScopeType().equals(ZtDataScopeTypeEnum.SELF)
                            && t.getDataScopeOptType().equals(ZtQueryTypeEnum.AND)
                            ;
                }).findAny();
                if (情况1.isPresent()) {
                    String userCodeFieldStr = ZtColumnUtil.getFieldName(getUserCodeField());
                    Field userCodeField = ZtUtils.getField(entity, userCodeFieldStr);
                    userCodeField.setAccessible(true);
                    if (userInfo != null) {
                        userCodeField.set(entity, userInfo.getUserCode());
                    } else {
                        userCodeField.set(entity, "zt");
                    }
                    ztQueryWrapper.andEquals(getUserCodeField());
                } else {
                    Optional<ZtRoleInfo> 情况2 = ztRoleInfoList.stream().filter(t -> {
                        return t.getDataScopeType().equals(ZtDataScopeTypeEnum.ALL);
                    }).findAny();
                    if (情况2.isPresent()) {
                        //这里不用处理，超级管理员可能会查询指定用户的数据，根据前端传的查询条件处理
                        //修改ztBeforeSimpleSelectProvider，添加查询条件
                    } else {
                        if (dataScopeDeptFlag()) {
                            List<ZtRoleInfo> 情况3_AND_1 = ztRoleInfoList.stream().filter(t -> {
                                return t.getDataScopeType().equals(ZtDataScopeTypeEnum.DEPT_ALL)
                                        && t.getDataScopeOptType().equals(ZtQueryTypeEnum.AND)
                                        ;
                            }).collect(Collectors.toList());
                            List<ZtRoleInfo> 情况3_AND_2 = ztRoleInfoList.stream().filter(t -> {
                                return t.getDataScopeType().equals(ZtDataScopeTypeEnum.DEPT_CUSTOM)
                                        && t.getDataScopeOptType().equals(ZtQueryTypeEnum.AND)
                                        ;
                            }).collect(Collectors.toList());

                            List<ZtRoleInfo> 情况3_OR_1 = ztRoleInfoList.stream().filter(t -> {
                                return t.getDataScopeType().equals(ZtDataScopeTypeEnum.DEPT_ALL)
                                        && t.getDataScopeOptType().equals(ZtQueryTypeEnum.AND)
                                        ;
                            }).collect(Collectors.toList());
                            List<ZtRoleInfo> 情况3_OR_2 = ztRoleInfoList.stream().filter(t -> {
                                return t.getDataScopeType().equals(ZtDataScopeTypeEnum.DEPT_CUSTOM)
                                        && t.getDataScopeOptType().equals(ZtQueryTypeEnum.AND)
                                        ;
                            }).collect(Collectors.toList());

                            List<ZtRoleInfo> deptRoleList = new LinkedList<>();
                            deptRoleList.addAll(情况3_AND_1);
                            deptRoleList.retainAll(情况3_AND_2);
                            deptRoleList.addAll(情况3_OR_1);
                            deptRoleList.addAll(情况3_OR_2);
                            deptRoleList = deptRoleList.stream().filter(ZtColumnUtil.distinctByKey(ZtRoleInfo::getThisCode)).collect(Collectors.toList());
                            List<ZtRoleInfo> deptCustomRoleList = deptRoleList.stream().filter(t -> t.getDataScopeType().equals(ZtDataScopeTypeEnum.DEPT_CUSTOM)).collect(Collectors.toList());
                            List<ZtRoleInfo> deptAllRoleList = deptRoleList.stream().filter(t -> t.getDataScopeType().equals(ZtDataScopeTypeEnum.DEPT_ALL)).collect(Collectors.toList());

                            Set<String> deptCodeSet = new HashSet<>();
                            for (ZtRoleInfo ztRoleInfo : deptCustomRoleList) {
                                deptCodeSet.addAll(ztRoleInfo.getRoleCustom());
                            }
                            for (ZtRoleInfo ztRoleInfo : deptAllRoleList) {
                                //TODO 查询ZtRoleDeptInfo
                                // deptCodeSet.addAll();
                            }
                            List<String> deptCodeList = new LinkedList<>(deptCodeSet);
                            ztQueryWrapper.andIn(getDeptCodeField(), deptCodeList);
                        }
                        if (dataScopeUserFlag()) {

                        }
                    }
                }
            }
        }
        return ztParamEntity;
    }

}
