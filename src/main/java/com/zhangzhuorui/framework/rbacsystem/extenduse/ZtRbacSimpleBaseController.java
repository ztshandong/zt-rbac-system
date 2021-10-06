package com.zhangzhuorui.framework.rbacsystem.extenduse;

import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.mybatis.simplebasecontroller.ZtSimpleBaseController;
import com.zhangzhuorui.framework.rbacsystem.aspects.ZtPreAuthorize;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtButtonCodeEnum;

/**
 * @author :  张涛 zhangtao
 * @version :  1.0
 * @createDate :  2017-01-01
 * @description : 扩展使用
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
public abstract class ZtRbacSimpleBaseController<T extends ZtRbacBasicEntity> extends ZtSimpleBaseController<T> {

    @Override
    @ZtPreAuthorize(hasAnyPermi = {ZtButtonCodeEnum.QUERY_CODE})
    protected ZtParamEntity<T> beforeSelect(ZtParamEntity<T> ztParamEntity) {
        return super.beforeSelect(ztParamEntity);
    }

    @Override
    @ZtPreAuthorize(hasAnyPermi = {ZtButtonCodeEnum.ADD_CODE})
    protected ZtParamEntity<T> beforeInsert(ZtParamEntity<T> ztParamEntity) {
        return super.beforeInsert(ztParamEntity);
    }

    @Override
    @ZtPreAuthorize(hasAnyPermi = {ZtButtonCodeEnum.EDIT_CODE})
    protected ZtParamEntity<T> beforeUpdate(ZtParamEntity<T> ztParamEntity) {
        return super.beforeUpdate(ztParamEntity);
    }

    @Override
    @ZtPreAuthorize(hasAnyPermi = {ZtButtonCodeEnum.DEL_CODE})
    protected ZtParamEntity<T> beforeDelete(ZtParamEntity<T> ztParamEntity) {
        return super.beforeDelete(ztParamEntity);
    }

    @Override
    @ZtPreAuthorize(hasAnyPermi = {ZtButtonCodeEnum.ADD_CODE})
    protected ZtParamEntity<T> beforeInsertBatch(ZtParamEntity<T> ztParamEntity) {
        return super.beforeInsertBatch(ztParamEntity);
    }

    @Override
    @ZtPreAuthorize(hasAnyPermi = {ZtButtonCodeEnum.DEL_CODE})
    protected ZtParamEntity<T> beforeDeleteBatch(ZtParamEntity<T> ztParamEntity) {
        return super.beforeDeleteBatch(ztParamEntity);
    }
}
