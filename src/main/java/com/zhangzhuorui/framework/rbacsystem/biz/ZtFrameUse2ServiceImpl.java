package com.zhangzhuorui.framework.rbacsystem.biz;

import com.zhangzhuorui.framework.core.ZtPropertyFunc;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author :  张涛 zhangtao
 * @version :  1.0
 * @createDate :  2017-01-01
 * @description : 开发框架示例 常规用法
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@Service
public class ZtFrameUse2ServiceImpl extends ZtRbacSimpleBaseServiceImpl<ZtFrameUse2> implements IZtFrameUse2Service {

    @Override
    public String getTableName() {
        return "zt_frame_use2";
    }

    /**
     * 启用职位或用户级别数据权限
     *
     * @return
     */
    @Override
    public Boolean dataScopeUserFlag() {
        return true;
    }

    @Override
    public ZtPropertyFunc<ZtFrameUse2, ?> getUserCodeField() {
        return ZtFrameUse2::getUserCode;
    }
}

