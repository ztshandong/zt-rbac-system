package com.zhangzhuorui.framework.rbacsystem.biz;

import com.zhangzhuorui.framework.core.ZtResBeanEx;
import com.zhangzhuorui.framework.mybatis.core.ZtParamEntity;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacSimpleBaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :  张涛 zhangtao
 * @version :  1.0
 * @createDate :  2017-01-01
 * @description : 开发框架示例3 高级用法
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@Api
@RestController
@RequestMapping(value = "/ZtFrameUse3")
public class ZtFrameUse3Controller extends ZtRbacSimpleBaseController<ZtFrameUse3> {

    @Override
    protected IZtFrameUse3Service getIZtSimpleBaseService() {
        return (IZtFrameUse3Service) super.getIZtSimpleBaseService();
    }

    //高级使用
    @RequestMapping(value = "advancedInstructions", method = RequestMethod.POST)
    @ResponseBody
    public ZtResBeanEx advancedInstructions(@RequestBody ZtFrameUse3 ztFrameUse3) throws Exception {
        ZtParamEntity<ZtFrameUse3> ztFrameUse3ZtParamEntity = getIZtSimpleBaseService().advancedInstructions(ztFrameUse3);
        ZtResBeanEx ztResBeanEx = ztFrameUse3ZtParamEntity.getZtResBeanEx();
        return ztResBeanEx;
    }
}

