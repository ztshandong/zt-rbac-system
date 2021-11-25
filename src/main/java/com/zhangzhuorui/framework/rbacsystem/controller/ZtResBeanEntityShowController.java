package com.zhangzhuorui.framework.rbacsystem.controller;

import com.zhangzhuorui.framework.core.ZtResBeanExList4Swagger;
import com.zhangzhuorui.framework.core.ZtResBeanExObj4Swagger;
import com.zhangzhuorui.framework.core.ZtStrUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :  张涛 zhangtao
 * @version :  1.0
 * @createDate :  2017
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@Api(tags = "1前端必看。返回体数据结构说明", description = "ZtResBeanEntityShowController")
@RestController
@RequestMapping(value = "/ZtResBeanEntityShowController")
public class ZtResBeanEntityShowController {

    @ApiOperation(value = "标准接口。根据id获取详情返回值示例。swagger无法显示返回体，所以额外用一个接口展示。请查看返回体Model描述")
    @RequestMapping(value = ZtStrUtils.SELECT_ID_SIMPLE, method = RequestMethod.POST)
    @ResponseBody
    public ZtResBeanExObj4Swagger showZtResBeanObj4Swagger() {
        return new ZtResBeanExObj4Swagger();
    }

    @ApiOperation(value = "标准接口，分页查询返回值示例。swagger无法显示返回体，所以额外用一个接口展示。请查看返回体Model描述")
    @RequestMapping(value = ZtStrUtils.SELECT_SIMPLE, method = RequestMethod.POST)
    @ResponseBody
    public ZtResBeanExList4Swagger showZtResBeanList4Swagger() {
        return new ZtResBeanExList4Swagger();
    }
}
