package com.zhangzhuorui.framework.rbacsystem.biz;

import com.zhangzhuorui.framework.core.ZtColumnUtil;
import com.zhangzhuorui.framework.core.ZtPropertyFunc;
import com.zhangzhuorui.framework.core.ZtQueryConditionEntity;
import com.zhangzhuorui.framework.core.ZtQueryTypeEnum;
import com.zhangzhuorui.framework.core.ZtQueryWrapperEnum;
import com.zhangzhuorui.framework.mybatis.core.ZtJoinWrapper;
import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.mybatis.core.ZtQueryWrapper;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author :  张涛 zhangtao
 * @version :  1.0
 * @createDate :  2017-01-01
 * @description : 开发框架示例3 高级用法
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@Service
public class ZtFrameUse3ServiceImpl extends ZtRbacSimpleBaseServiceImpl<ZtFrameUse3> implements IZtFrameUse3Service {

    @Override
    public String getTableName() {
        return "zt_frame_use3";
    }

    /**
     * 同时启用部门及职位用户数据权限
     *
     * @return
     */
    @Override
    public Boolean dataScopeDeptFlag() {
        return true;
    }

    /**
     * 同时启用部门及职位用户数据权限
     *
     * @return
     */
    @Override
    public Boolean dataScopeUserFlag() {
        return true;
    }

    @Override
    public ZtPropertyFunc<ZtFrameUse3, ?> getDeptCodeField() {
        return ZtFrameUse3::getBizDeptCode;
    }

    @Override
    public ZtPropertyFunc<ZtFrameUse3, ?> getUserCodeField() {
        return ZtFrameUse3::getBizUserCode;
    }

    /**
     * 乐观锁
     *
     * @return
     */
    @Override
    public String getVersionFieldName() {
        return ZtColumnUtil.getFieldName(ZtFrameUse3::getVersion);
    }

    /**
     * 手动插入主键
     *
     * @return
     */
    @Override
    public Boolean getManualId() {
        return true;
    }

    /**
     * 逻辑删除
     *
     * @return
     */
    @Override
    public String getLogicDeleteFieldName() {
        return ZtColumnUtil.getFieldName(ZtFrameUse3::getDeleteFlag);
    }

    @Autowired
    IZtFrameUse1Service iZtFrameUse1Service;

    @Autowired
    IZtFrameUse2Service iZtFrameUse2Service;

    /*
    SELECT DISTINCT
        0b315_zt_frame_use2.`created_by` AS createdBy,
        0b315_zt_frame_use2.`created_by_name` AS createdByName,
        0b315_zt_frame_use2.`updated_by_name` AS otherParams,
        t1.`created_by` AS createdBy,
        t1.`created_by_name` AS createdByName,
        t1.`del_flag` AS delFlag,
        t1.`dept_code` AS deptCode,
        t1.`enable_flag` AS enableFlag,
        t1.`enum_1` AS enum1,
        t1.`gmt_create` AS gmtCreate,
        t1.`id` AS id,
        t1.`remark` AS remark,
        t1.`udf_1` AS udf1,
        t1.`udf_2` AS udf2,
        t1.`updated_by` AS updatedBy,
        t1.`updated_by_name` AS updatedByName,
        zt_frame_use3.`biz_dept_code` AS bizDeptCode,
        zt_frame_use3.`biz_user_code` AS bizUserCode,
        zt_frame_use3.`created_by` AS createdBy,
        zt_frame_use3.`created_by_name` AS createdByName,
        zt_frame_use3.`delete_flag` AS deleteFlag,
        zt_frame_use3.`enum_1` AS enum1,
        zt_frame_use3.`gmt_update` AS gmtUpdate,
        zt_frame_use3.`id` AS id,
        zt_frame_use3.`remark` AS remark,
        zt_frame_use3.`udf_1` AS udf1,
        zt_frame_use3.`udf_2` AS udf2,
        zt_frame_use3.`updated_by` AS updatedBy,
        zt_frame_use3.`updated_by_name` AS updatedByName,
        zt_frame_use3.`version` AS version
    FROM
        zt_frame_use3
        LEFT JOIN zt_frame_use1 AS t1 ON t1.`udf_1` = zt_frame_use3.`udf_1`
        INNER JOIN zt_frame_use2 AS 0b315_zt_frame_use2 ON 0b315_zt_frame_use2.`id` = t1.`udf_2`
    WHERE
        1 = 1
        AND ( zt_frame_use3.`delete_flag` = FALSE )
        AND ( zt_frame_use3.`udf_1` = 'string' )
        AND ( zt_frame_use3.`biz_dept_code` IN ( 'shanghaihr', 'shanghairoot', 'beijingroot', 'shanghaidevelop' ) )
        AND ( zt_frame_use3.`id` IN ( SELECT id FROM zt_frame_use2 WHERE 1 = 1 OR ( zt_frame_use2.`user_code` IN ( 'zhangtao' ) ) ) )
        OR ( zt_frame_use3.`biz_user_code` IN ( 'zhangtao' ) )
        AND (
            1 = 1
            AND ( zt_frame_use3.`delete_flag` = FALSE )
            AND ( zt_frame_use3.`biz_dept_code` IN ( 'shanghaihr', 'shanghairoot', 'beijingroot', 'shanghaidevelop' ) )
            AND ( zt_frame_use3.`created_by` IN ( '王五', '赵六' ) )
            OR ( zt_frame_use3.`remark` LIKE '%%备注%%' )
            OR ( zt_frame_use3.`biz_user_code` IN ( 'zhangtao' ) )
            OR ( zt_frame_use3.`created_by` IN ( '张三', '李四' ) )
        )
        OR (
            1 = 1
            AND ( zt_frame_use3.`delete_flag` = FALSE )
            AND ( zt_frame_use3.`remark` LIKE '%%备注%%' )
            AND ( zt_frame_use3.`biz_dept_code` IN ( 'shanghaihr', 'shanghairoot', 'beijingroot', 'shanghaidevelop' ) )
            AND ( zt_frame_use3.`created_by` IN ( '张三', '李四' ) )
            OR ( zt_frame_use3.`biz_user_code` IN ( 'zhangtao' ) )
        )
        AND ( t1.`dept_code` IN ( 'shanghaihr', 'shanghairoot', 'beijingroot', 'shanghaidevelop' ) )
        AND ( t1.`gmt_update` IS NOT NULL )
        OR ( t1.`remark` LIKE '%aaa%' )
        OR ( t1.`udf_1` IN ( 'bbb', 'ccc' ) )
        AND (
            1 = 1
            AND ( t1.`dept_code` IN ( 'shanghaihr', 'shanghairoot', 'beijingroot', 'shanghaidevelop' ) )
            AND ( t1.`gmt_update` IS NOT NULL )
            OR ( t1.`remark` LIKE '%inneraaa%' )
            OR ( t1.`udf_1` IN ( 'innerbbb', 'innerccc' ) )
            OR ( t1.`id` NOT IN ( SELECT id FROM zt_frame_use2 WHERE 1 = 1 OR ( zt_frame_use2.`user_code` IN ( 'zhangtao' ) ) ) )
        )
        AND ( 0b315_zt_frame_use2.`remark` IN ( 'fff', 'ggg' ) )
        OR ( 0b315_zt_frame_use2.`udf_1` LIKE '%d%' )
        OR ( 0b315_zt_frame_use2.`user_code` IN ( 'zhangtao' ) )
        LIMIT 0,1000
     */
    @Override
    public ZtParamEntity<ZtFrameUse3> advancedInstructions(ZtFrameUse3 ztFrameUse3) throws Exception {

        ZtParamEntity<ZtFrameUse3> ztRootParamEntity = getInitZtParamEntityWithOutCount(ztFrameUse3);
        //这个是最大的查询条件，就是SELECT xx FROM zt_frame_use3 ，后面的都是被JOIN的表
        ZtQueryWrapper<ZtFrameUse3> ztRootQueryWrapper = ztRootParamEntity.getZtQueryWrapper();

        //根据sql子查询，通常是查询其他表
        ZtFrameUse2 ztFrameUse2SubQuery = new ZtFrameUse2();
        ZtQueryWrapper<ZtFrameUse2> ztFrameUse2SubQueryWrapper = iZtFrameUse2Service.getInitZtQueryWrapper(ztFrameUse2SubQuery);
        String id = iZtFrameUse2Service.getColumnName(ZtFrameUse2::getId);
        ztFrameUse2SubQueryWrapper.setSelectColumn(id);
        //AND ( zt_frame_use3.id IN ( SELECT id FROM zt_frame_use2 WHERE 1 = 1 ) )
        ztRootQueryWrapper.andIn(ZtFrameUse3::getId, ztFrameUse2SubQueryWrapper);

        //复合查询
        ZtFrameUse3 ztFrameUse3And = new ZtFrameUse3();
        ztFrameUse3And.setRemark("备注");
        ZtQueryWrapper<ZtFrameUse3> ztFrameUse3AndInnerQueryWrapper = getThisService().getInitZtQueryWrapper(ztFrameUse3And);
        ztFrameUse3AndInnerQueryWrapper
                .orLike(ZtFrameUse3::getRemark)
                .orIn(ZtFrameUse3::getCreatedBy, new ArrayList() {{
                    add("张三");
                    add("李四");
                }});
        LinkedList<ZtQueryConditionEntity> conditons = ztFrameUse3AndInnerQueryWrapper.getConditons();
        //同一个字段，多个条件
        ZtQueryConditionEntity tmp = new ZtQueryConditionEntity();
        tmp.setQueryWrapper(ZtQueryWrapperEnum.IN);
        tmp.setList(new ArrayList() {{
            add("王五");
            add("赵六");
        }});
        tmp.setQueryType(ZtQueryTypeEnum.AND);
        String fieldName = ZtColumnUtil.getFieldName(ZtFrameUse3::getCreatedBy);
        tmp.setFieldName(fieldName);
        String columnName = getColumnName(fieldName);
        tmp.setColumnName(columnName);
        conditons.add(tmp);

        //AND ( ( zt_frame_use3.`created_by` IN ( '王五', '赵六' ) ) OR ( zt_frame_use3.remark LIKE '%%备注%%' ) OR ( zt_frame_use3.created_by IN ( '张三', '李四' ) ) )
        ztRootQueryWrapper.andInnerQueryWrapper(ztFrameUse3AndInnerQueryWrapper);

        //复合查询
        ZtFrameUse3 ztFrameUse3Or = new ZtFrameUse3();
        ztFrameUse3Or.setRemark("ggggg");
        ZtQueryWrapper<ZtFrameUse3> ztFrameUse3OrInnerQueryWrapper = getThisService().getInitZtQueryWrapper(ztFrameUse3And);
        ztFrameUse3OrInnerQueryWrapper
                .andLike(ZtFrameUse3::getRemark)
                .andIn(ZtFrameUse3::getCreatedBy, new ArrayList() {{
                    add("张三");
                    add("李四");
                }});
        //OR ( ( zt_frame_use3.remark LIKE '%%备注%%' ) AND ( zt_frame_use3.created_by IN ( '张三', '李四' ) )
        ztRootQueryWrapper.orInnerQueryWrapper(ztFrameUse3OrInnerQueryWrapper);

        //ZtFrameUse1对应的查询条件
        //AND ( t1.gmt_update IS NOT NULL ) OR ( t1.remark LIKE '%aaa%' ) OR ( t1.udf_1 IN ( 'bbb', 'ccc' ) )
        ZtFrameUse1 ztFrameUse1 = new ZtFrameUse1();
        ztFrameUse1.setRemark("aaa");
        ZtQueryWrapper<ZtFrameUse1> ztFrameUse1QueryWrapper = iZtFrameUse1Service.getInitZtQueryWrapper(ztFrameUse1);
        ztFrameUse1QueryWrapper
                .andIsNotNull(ZtFrameUse1::getGmtUpdate)
                .orLike(ZtFrameUse1::getRemark)
                .orIn(ZtFrameUse1::getUdf1, new ArrayList() {{
                    add("bbb");
                    add("ccc");
                }});

        ZtFrameUse1 ztFrameUse1Inner = new ZtFrameUse1();
        ztFrameUse1Inner.setRemark("inneraaa");
        ZtQueryWrapper<ZtFrameUse1> ztFrameUse1InnerQueryWrapper = iZtFrameUse1Service.getInitZtQueryWrapper(ztFrameUse1Inner);
        ztFrameUse1InnerQueryWrapper
                .andIsNotNull(ZtFrameUse1::getGmtUpdate)
                .orLike(ZtFrameUse1::getRemark)
                .orIn(ZtFrameUse1::getUdf1, new ArrayList() {{
                    add("innerbbb");
                    add("innerccc");
                }})
                .orNotIn(ZtFrameUse1::getId, ztFrameUse2SubQueryWrapper);
        //被JOIN的表嵌套子查询
        //AND ( ( t1.gmt_update IS NOT NULL ) OR ( t1.remark LIKE '%inneraaa%' ) OR ( t1.udf_1 IN ( 'innerbbb', 'innerccc' )
        // OR ( t1.id NOT IN ( SELECT id FROM FrameUse2 WHERE 1 = 1 ) ) )
        ztFrameUse1QueryWrapper.andInnerQueryWrapper(ztFrameUse1InnerQueryWrapper);

        //ZtFrameUse2对应的查询条件
        //	AND ( 5ca68_zt_frame_use2.remark IN ( 'fff', 'ggg' ) ) OR ( 5ca68_zt_frame_use2.udf_1 LIKE '%d%' )
        ZtFrameUse2 ztFrameUse2 = new ZtFrameUse2();
        ztFrameUse2.setUdf1("d");
        ztFrameUse2.setRemark("eee");
        ZtQueryWrapper<ZtFrameUse2> ztFrameUse2QueryWrapper = iZtFrameUse2Service.getInitZtQueryWrapper(ztFrameUse2);
        ztFrameUse2QueryWrapper
                .orLike(ZtFrameUse2::getUdf1)
                .andIn(ZtFrameUse2::getRemark, new ArrayList() {{
                    add("fff");
                    add("ggg");
                }});

        //默认别名
        ZtJoinWrapper<ZtFrameUse2> ztFrameUse2JoinWrapper = new ZtJoinWrapper<>(ztFrameUse2QueryWrapper);
        //指定别名
        ZtJoinWrapper<ZtFrameUse1> ztFrameUse1JoinWrapper = new ZtJoinWrapper<>("t1", ztFrameUse1QueryWrapper);

        //FROM zt_frame_use3 LEFT JOIN zt_frame_use1 as t1 ON t1.udf1 = zt_frame_use3.udf1   rightWrapper为null就是ztRootQueryWrapper
        ztRootQueryWrapper.leftJoin(ztFrameUse1JoinWrapper, ZtFrameUse1::getUdf1, null, ZtFrameUse3::getUdf1);
        //INNER JOIN zt_frame_use2 as 8s9h3_zt_frame_use2 ON 8s9h3_zt_frame_use2.id = t1.udf2
        ztRootQueryWrapper.innerJoin(ztFrameUse2JoinWrapper, ZtFrameUse2::getId, ztFrameUse1JoinWrapper, ZtFrameUse1::getUdf2);

        /**
         * 一定要ztRootQueryWrapper.join之后操作列
         * 联表查询会取别名，因为联表查询结果解析不是用的ResultMap
         */
        //select添加全部列 joinWrapper为null就是ztRootQueryWrapper对应的表
        ztRootQueryWrapper.addAllColumn(null);
        ztRootQueryWrapper.addAllColumn(ztFrameUse1JoinWrapper);

        //select去除不需要的列
        ztRootQueryWrapper.removeSelectColumn(null, ZtFrameUse3::getGmtCreate);
        ztRootQueryWrapper.removeSelectColumn(ztFrameUse1JoinWrapper, ZtFrameUse1::getGmtUpdate);

        //等于没操作
        ztRootQueryWrapper.addSelectColumn(null, ZtFrameUse3::getId, null);
        //修改原有的别名
        ztRootQueryWrapper.addSelectColumn(ztFrameUse1JoinWrapper, ZtFrameUse1::getId, ZtFrameUse1::getId);
        //手动添加列
        ztRootQueryWrapper.addSelectColumn(ztFrameUse2JoinWrapper, ZtFrameUse2::getCreatedByName, null);
        ztRootQueryWrapper.addSelectColumn(ztFrameUse2JoinWrapper, ZtFrameUse2::getCreatedBy, null);
        ztRootQueryWrapper.addSelectColumn(ztFrameUse2JoinWrapper, ZtFrameUse2::getUpdatedByName, ZtFrameUse1::getOtherParams);
        //返回的entity
        ztRootParamEntity.setDtoClass(ZtFrameUse1.class);
        ztRootParamEntity = getThisService().ztDoSimpleJoinSelectProvider(ztRootParamEntity);
        return ztRootParamEntity;
    }

    @Override
    public ZtFrameUse3 testUpdateUseNativeSql(ZtFrameUse3 ztFrameUse3) throws Exception {
        ZtParamEntity<ZtFrameUse3> ztTest3ZtParamEntity = getInitZtParamEntity(ztFrameUse3);
        ZtQueryWrapper ztQueryWrapper = ztTest3ZtParamEntity.getZtQueryWrapper();
        LinkedList<ZtQueryConditionEntity> conditons = ztQueryWrapper.getConditons();
        String fieldName = ZtColumnUtil.getFieldName(ZtFrameUse3::getUdf2);
        ZtQueryConditionEntity ztQueryConditionEntity = conditons.stream().filter(t -> t.getFieldName().equals(fieldName)).findAny().get();
        ztQueryConditionEntity.setUpdateFieldUseNativeSql(true);
        ztQueryConditionEntity.setUpdateFieldNativeSql(" ( udf_2 + 10 ) ");
        ztTest3ZtParamEntity = getThisService().ztSimpleUpdateByPrimaryKey(ztTest3ZtParamEntity);
        //这里不是更新后的值
        return ztFrameUse3;
    }
}

