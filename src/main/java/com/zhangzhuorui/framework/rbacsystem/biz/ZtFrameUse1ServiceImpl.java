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
public class ZtFrameUse1ServiceImpl extends ZtRbacSimpleBaseServiceImpl<ZtFrameUse1> implements IZtFrameUse1Service {

    @Override
    public String getTableName() {
        return "zt_frame_use1";
    }

    /**
     * 启用部门级别数据权限
     *
     * @return
     */
    @Override
    public Boolean dataScopeDeptFlag() {
        return true;
    }

    @Override
    public ZtPropertyFunc<ZtFrameUse1, ?> getDeptCodeField() {
        return ZtFrameUse1::getDeptCode;
    }
}

