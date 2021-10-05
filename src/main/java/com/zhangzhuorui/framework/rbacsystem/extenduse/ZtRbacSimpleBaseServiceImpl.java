package com.zhangzhuorui.framework.rbacsystem.extenduse;

import com.zhangzhuorui.framework.core.ZtColumnUtil;
import com.zhangzhuorui.framework.core.ZtQueryConditionEntity;
import com.zhangzhuorui.framework.core.ZtQueryInHelper;
import com.zhangzhuorui.framework.core.ZtQueryTypeEnum;
import com.zhangzhuorui.framework.core.ZtQueryWrapperEnum;
import com.zhangzhuorui.framework.core.ZtUtils;
import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.mybatis.core.ZtQueryWrapper;
import com.zhangzhuorui.framework.mybatis.simplebaseservice.ZtSimpleBaseServiceImpl;
import com.zhangzhuorui.framework.rbacsystem.config.ZtCacheUtil;
import com.zhangzhuorui.framework.rbacsystem.config.ZtJwtTokenUtil;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtComponentInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtRoleInfo;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtDataScopeTypeEnum;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtRoleTypeEnum;
import com.zhangzhuorui.framework.rbacsystem.service.IZtDeptInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtRoleInfoService;
import com.zhangzhuorui.framework.rbacsystem.service.IZtUserInfoService;
import lombok.SneakyThrows;
import org.apache.ibatis.mapping.SqlCommandType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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
    protected ZtCacheUtil ztCacheUtil;

    @Autowired
    protected ZtJwtTokenUtil ztJwtTokenUtil;

    @Autowired
    IZtRoleInfoService iZtRoleInfoService;

    @Autowired
    IZtDeptInfoService iZtDeptInfoService;

    @Autowired
    IZtUserInfoService iZtUserInfoService;

    @Override
    public ZtUserInfo getSimpleUserInfoFromToken() {
        String token = getRequest().getHeader(ztJwtTokenUtil.getTokenHeader());
        ZtUserInfo userInfoFromToken = ztJwtTokenUtil.getUserInfoFromToken(token);
        return userInfoFromToken;
    }

    /**
     * List组装成为树
     *
     * @param parentNode :
     * @param treeNodes  :
     * @return :  com.zhangzhuorui.framework.rbacsystem.entity.ZtComponentInfo
     * @author :  zhangtao
     * @createDate :  2021/10/5 下午7:49
     * @description :
     * @updateUser :
     * @updateDate :
     * @updateRemark :
     */
    @Override
    public ZtComponentInfo buildComponentTree(ZtComponentInfo parentNode, List<ZtComponentInfo> treeNodes) {

        List<ZtComponentInfo> children = parentNode.getChildren();
        // if (children == null) {
        //     children = new LinkedList<>();
        //     parentNode.setChildren(children);
        // }
        for (ZtComponentInfo ztComponentInfo : treeNodes) {
            if (parentNode.getThisCode().equals(ztComponentInfo.getParentCode())) {
                if (!children.contains(ztComponentInfo)) {
                    ZtComponentInfo ztComponentInfo1 = new ZtComponentInfo();
                    BeanUtils.copyProperties(ztComponentInfo, ztComponentInfo1);
                    children.add(buildComponentTree(ztComponentInfo1, treeNodes));
                }
            }
        }
        return parentNode;
    }

    /**
     * 数据权限 判断 ZtDataScopeTypeEnum 与 ZtQueryTypeEnum
     * <p>
     * 1.用户的角色中，如果有角色：ZtDataScopeTypeEnum是SELF，ZtQueryTypeEnum是AND，忽略其他条件，只能看自己（最高优先级）
     * 不是情况1:
     * 2.用户的角色中，如果有超级管理员角色或者：ZtDataScopeTypeEnum是ALL，不论ZtQueryTypeEnum是什么，可以看所有人（次高优先级）
     * 不是情况1和情况2:
     * dataScopeDeptFlag()
     * 3.DEPT_CUSTOM如果即有AND又有OR，复杂查询
     * 4.DEPT_CUSTOM如果只有AND或OR，单一查询  getCurUserDataRoleAndDeptCodes或getCurUserDataRoleOrDeptCodes
     * 5.没有DEPT_CUSTOM，使用iZtDeptInfoService.getCurUserDeptCodes，条件为AND
     * dataScopeUserFlag()
     * 6.POST_CUSTOM或USER_CUSTOM如果即有AND又有OR，复杂查询
     * 7.POST_CUSTOM或USER_CUSTOM如果只有AND或OR，单一查询 getCurUserDataRoleAndUserCodes或getCurUserDataRoleOrUserCodes
     * 8.如果没有POST_CUSTOM与USER_CUSTOM，使用getCurUserDataRoleOrUserCodes，SQL为OR
     */
    @SneakyThrows
    @Override
    public ZtParamEntity<T> afterUseCommonZtQueryWrapper(ZtParamEntity<T> ztParamEntity, SqlCommandType sqlCommandType) {
        if (sqlCommandType.equals(SqlCommandType.SELECT)) {
            T entity = ztParamEntity.getEntity();
            if (entity.getDataScopeFlag() == null || entity.getDataScopeFlag()) {
                ZtQueryWrapper<T> ztQueryWrapper = ztParamEntity.getZtQueryWrapper();
                if (dataScopeDeptFlag() || dataScopeUserFlag()) {
                    ZtUserInfo userInfo = (ZtUserInfo) ztParamEntity.getUserInfo();
                    if (userInfo == null) {
                        userInfo = getSimpleUserInfoFromToken();
                        userInfo = iZtUserInfoService.getFullUserInfoFromToken(userInfo);
                    }
                    List<String> curUserAllRoleCodes = iZtRoleInfoService.getCurUserAllRoleCodes(userInfo);
                    ZtParamEntity<ZtRoleInfo> ztRoleInfoZtParamEntity = iZtRoleInfoService.ztSimpleSelectAll();
                    List<ZtRoleInfo> curUserDataRoleInfoList = iZtRoleInfoService.getList(ztRoleInfoZtParamEntity);
                    curUserDataRoleInfoList = curUserDataRoleInfoList.stream().filter(t -> !ZtRoleTypeEnum.COMPONENT.equals(t.getRoleType()) && curUserAllRoleCodes.contains(t.getThisCode())).collect(Collectors.toList());
                    //1
                    Optional<ZtRoleInfo> onlySelfRole = curUserDataRoleInfoList.stream().filter(t -> {
                        return t.getDataScopeType().equals(ZtDataScopeTypeEnum.SELF)
                                && t.getDataScopeOptType().equals(ZtQueryTypeEnum.AND)
                                ;
                    }).findAny();
                    if (onlySelfRole.isPresent()) {
                        String userCodeFieldStr = ZtColumnUtil.getFieldName(getUserCodeField());
                        Field userCodeField = ZtUtils.getField(entity, userCodeFieldStr);
                        userCodeField.setAccessible(true);
                        userCodeField.set(entity, userInfo.getUserCode());
                        ztQueryWrapper.andEquals(getUserCodeField());
                    } else {
                        //2
                        Optional<ZtRoleInfo> allDataRole = curUserDataRoleInfoList.stream().filter(t -> {
                            return t.getDataScopeType().equals(ZtDataScopeTypeEnum.ALL) || t.getAdminFlag();
                        }).findAny();
                        if (allDataRole.isPresent()) {
                            //这里不用处理，超级管理员可能会查询指定用户的数据，根据前端传的查询条件处理
                            //修改ztBeforeSimpleSelectProvider，添加查询条件
                        } else {
                            if (dataScopeDeptFlag()) {
                                List<ZtRoleInfo> deptCustomAnd = curUserDataRoleInfoList.stream().filter(t -> {
                                    return t.getDataScopeType().equals(ZtDataScopeTypeEnum.DEPT_CUSTOM)
                                            && t.getDataScopeOptType().equals(ZtQueryTypeEnum.AND)
                                            ;
                                }).collect(Collectors.toList());

                                List<ZtRoleInfo> deptCustomOr = curUserDataRoleInfoList.stream().filter(t -> {
                                    return t.getDataScopeType().equals(ZtDataScopeTypeEnum.DEPT_CUSTOM)
                                            && t.getDataScopeOptType().equals(ZtQueryTypeEnum.OR)
                                            ;
                                }).collect(Collectors.toList());

                                if (deptCustomAnd.size() > 0 && deptCustomOr.size() > 0) {
                                    //3
                                    List<String> curUserDataRoleAndDeptCodes = iZtRoleInfoService.getCurUserDataRoleAndDeptCodes(userInfo);
                                    ZtQueryInHelper ztQueryInHelper = new ZtQueryInHelper();
                                    ztQueryInHelper.setInSqlStr(ZtUtils.getSqlInStr(curUserDataRoleAndDeptCodes));
                                    ztQueryWrapper.andIn(getDeptCodeField(), ztQueryInHelper);

                                    List<String> curUserDataRoleOrDeptCodes = iZtRoleInfoService.getCurUserDataRoleOrDeptCodes(userInfo);
                                    LinkedList<ZtQueryConditionEntity> conditons = ztQueryWrapper.getConditons();
                                    //同一个字段，多个条件
                                    ZtQueryConditionEntity tmp = new ZtQueryConditionEntity();
                                    tmp.setQueryWrapper(ZtQueryWrapperEnum.IN);

                                    ZtQueryInHelper ztQueryInHelper2 = new ZtQueryInHelper();
                                    ztQueryInHelper2.setInSqlStr(ZtUtils.getSqlInStr(curUserDataRoleOrDeptCodes));

                                    tmp.setList(ztQueryInHelper2);
                                    tmp.setQueryType(ZtQueryTypeEnum.OR);
                                    String fieldName = ZtColumnUtil.getFieldName(getDeptCodeField());
                                    tmp.setFieldName(fieldName);
                                    String columnName = getColumnName(fieldName);
                                    tmp.setColumnName(columnName);
                                    conditons.add(tmp);

                                } else if (deptCustomAnd.size() > 0) {
                                    //4
                                    List<String> curUserDataRoleAndDeptCodes = iZtRoleInfoService.getCurUserDataRoleAndDeptCodes(userInfo);
                                    ZtQueryInHelper ztQueryInHelper = new ZtQueryInHelper();
                                    ztQueryInHelper.setInSqlStr(ZtUtils.getSqlInStr(curUserDataRoleAndDeptCodes));
                                    ztQueryWrapper.andIn(getDeptCodeField(), ztQueryInHelper);
                                } else if (deptCustomOr.size() > 0) {
                                    //4
                                    List<String> curUserDataRoleOrDeptCodes = iZtRoleInfoService.getCurUserDataRoleOrDeptCodes(userInfo);
                                    ZtQueryInHelper ztQueryInHelper = new ZtQueryInHelper();
                                    ztQueryInHelper.setInSqlStr(ZtUtils.getSqlInStr(curUserDataRoleOrDeptCodes));
                                    ztQueryWrapper.orIn(getDeptCodeField(), ztQueryInHelper);
                                } else {
                                    //5
                                    List<String> curUserDeptCodes = iZtDeptInfoService.getCurUserDeptCodes(userInfo);
                                    ZtQueryInHelper ztQueryInHelper = new ZtQueryInHelper();
                                    ztQueryInHelper.setInSqlStr(ZtUtils.getSqlInStr(curUserDeptCodes));
                                    ztQueryWrapper.andIn(getDeptCodeField(), ztQueryInHelper);
                                }
                            }
                            if (dataScopeUserFlag()) {
                                Boolean and, or;
                                Optional<ZtRoleInfo> postCustomAnd = curUserDataRoleInfoList.stream().filter(t -> {
                                    return t.getDataScopeType().equals(ZtDataScopeTypeEnum.POST_CUSTOM)
                                            && t.getDataScopeOptType().equals(ZtQueryTypeEnum.AND)
                                            ;
                                }).findAny();

                                Optional<ZtRoleInfo> userCustomAnd = curUserDataRoleInfoList.stream().filter(t -> {
                                    return t.getDataScopeType().equals(ZtDataScopeTypeEnum.USER_CUSTOM)
                                            && t.getDataScopeOptType().equals(ZtQueryTypeEnum.AND)
                                            ;
                                }).findAny();

                                and = postCustomAnd.isPresent() || userCustomAnd.isPresent();

                                Optional<ZtRoleInfo> postCustomOr = curUserDataRoleInfoList.stream().filter(t -> {
                                    return t.getDataScopeType().equals(ZtDataScopeTypeEnum.POST_CUSTOM)
                                            && t.getDataScopeOptType().equals(ZtQueryTypeEnum.OR)
                                            ;
                                }).findAny();

                                Optional<ZtRoleInfo> userCustomOr = curUserDataRoleInfoList.stream().filter(t -> {
                                    return t.getDataScopeType().equals(ZtDataScopeTypeEnum.USER_CUSTOM)
                                            && t.getDataScopeOptType().equals(ZtQueryTypeEnum.OR)
                                            ;
                                }).findAny();

                                or = postCustomOr.isPresent() || userCustomOr.isPresent();

                                if (and && or) {
                                    //6
                                    List<String> curUserDataRoleAndUserCodes = iZtRoleInfoService.getCurUserDataRoleAndUserCodes(userInfo);
                                    ZtQueryInHelper ztQueryInHelper = new ZtQueryInHelper();
                                    ztQueryInHelper.setInSqlStr(ZtUtils.getSqlInStr(curUserDataRoleAndUserCodes));
                                    ztQueryWrapper.andIn(getUserCodeField(), ztQueryInHelper);

                                    List<String> curUserDataRoleOrUserCodes = iZtRoleInfoService.getCurUserDataRoleOrUserCodes(userInfo);
                                    LinkedList<ZtQueryConditionEntity> conditons = ztQueryWrapper.getConditons();
                                    //同一个字段，多个条件
                                    ZtQueryConditionEntity tmp = new ZtQueryConditionEntity();
                                    tmp.setQueryWrapper(ZtQueryWrapperEnum.IN);

                                    ZtQueryInHelper ztQueryInHelper2 = new ZtQueryInHelper();
                                    ztQueryInHelper2.setInSqlStr(ZtUtils.getSqlInStr(curUserDataRoleOrUserCodes));

                                    tmp.setList(ztQueryInHelper2);
                                    tmp.setQueryType(ZtQueryTypeEnum.OR);
                                    String fieldName = ZtColumnUtil.getFieldName(getUserCodeField());
                                    tmp.setFieldName(fieldName);
                                    String columnName = getColumnName(fieldName);
                                    tmp.setColumnName(columnName);
                                    conditons.add(tmp);

                                } else if (and) {
                                    //7
                                    List<String> curUserDataRoleAndUserCodes = iZtRoleInfoService.getCurUserDataRoleAndUserCodes(userInfo);
                                    ZtQueryInHelper ztQueryInHelper = new ZtQueryInHelper();
                                    ztQueryInHelper.setInSqlStr(ZtUtils.getSqlInStr(curUserDataRoleAndUserCodes));
                                    ztQueryWrapper.andIn(getUserCodeField(), ztQueryInHelper);
                                } else if (or) {
                                    //7
                                    List<String> curUserDataRoleOrUserCodes = iZtRoleInfoService.getCurUserDataRoleOrUserCodes(userInfo);
                                    ZtQueryInHelper ztQueryInHelper = new ZtQueryInHelper();
                                    ztQueryInHelper.setInSqlStr(ZtUtils.getSqlInStr(curUserDataRoleOrUserCodes));
                                    ztQueryWrapper.orIn(getUserCodeField(), ztQueryInHelper);
                                } else {
                                    //8
                                    List<String> curUserDataRoleOrUserCodes = iZtRoleInfoService.getCurUserDataRoleOrUserCodes(userInfo);
                                    ZtQueryInHelper ztQueryInHelper = new ZtQueryInHelper();
                                    ztQueryInHelper.setInSqlStr(ZtUtils.getSqlInStr(curUserDataRoleOrUserCodes));
                                    ztQueryWrapper.orIn(getUserCodeField(), ztQueryInHelper);
                                }
                            }
                        }
                    }
                }
            }
        }
        return ztParamEntity;
    }
}
