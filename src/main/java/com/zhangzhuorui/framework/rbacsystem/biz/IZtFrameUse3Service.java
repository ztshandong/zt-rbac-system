package com.zhangzhuorui.framework.rbacsystem.biz;

import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.rbacsystem.extenduse.IZtRbacSimpleBaseService;

/**
 * @author :  张涛 zhangtao
 * @version :  1.0
 * @createDate :  2017-01-01
 * @description : 开发框架示例3 高级用法
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
public interface IZtFrameUse3Service extends IZtRbacSimpleBaseService<ZtFrameUse3> {

    ZtParamEntity<ZtFrameUse3> advancedInstructions(ZtFrameUse3 ztFrameUse3) throws Exception;

    ZtFrameUse3 testUpdateUseNativeSql(ZtFrameUse3 ztFrameUse3) throws Exception;
}


