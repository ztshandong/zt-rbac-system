package com.zhangzhuorui.framework.rbacsystem.controller;

import com.zhangzhuorui.framework.core.ZtResBeanEx4Swagger;
import com.zhangzhuorui.framework.core.ZtResBeanExList4Swagger;
import com.zhangzhuorui.framework.core.ZtResBeanExObj4Swagger;
import com.zhangzhuorui.framework.core.ZtStrUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiSort;
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
@ApiSort(value = 0)
@Api(tags = "前端必看。返回体数据结构说明", value = "前端必看。返回体数据结构说明", description = "ZtResBeanEntityShowController")
@RestController
@RequestMapping(value = "/ZtResBeanEntityShowController")
public class ZtResBeanEntityShowController {

    final String s = "项目统一返回格式\n" +
            "a-a-a-a-\n" +
            "{\n" +
            "\b \b \b \b \"result\"\b \b :\b \b {\n" +
            "\b \b \b \b \b \b \b \b \"data\"\b \b :\b \b {},\n" +
            "\b \b \b \b \b \b \b \b \"empty\"\b \b :\b \b true,\n" +
            "\b \b \b \b \b \b \b \b \"errCode\"\b \b :\b \b \"0\",\n" +
            "\b \b \b \b \b \b \b \b \"errMsg\"\b \b :\b \b \"ok\"\n" +
            "\b \b \b \b },\n" +
            "\b \b \b \b \"success\"\b \b :\b \b true\n" +
            "}\n" +
            "a-a-a-a-";

    //ZtStrUtils.NORMAL_RES_SWAGGER
    @ApiOperation(notes = ZtStrUtils.NORMAL_RES_SWAGGER, value = "项目统一返回格式。请查看返回体Model描述")
    @RequestMapping(value = "resModel", method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请查看Model描述")
    })
    public ZtResBeanEx4Swagger showZtResBean4Swagger() {
        return new ZtResBeanEx4Swagger();
    }

    @ApiOperation(notes = ZtStrUtils.SELECT_ID_SIMPLE_RES_SWAGGER, value = "返回单条数据示例")
    @RequestMapping(value = ZtStrUtils.SELECT_ID_SIMPLE, method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "返回结构示例如下")
    })
    public ZtResBeanExObj4Swagger showZtResBeanObj4Swagger() {
        return new ZtResBeanExObj4Swagger();
    }

    @ApiOperation(notes = ZtStrUtils.SELECT_SIMPLE_RES_SWAGGER, value = "分页查询返回多条数据示例")
    @RequestMapping(value = ZtStrUtils.SELECT_SIMPLE, method = RequestMethod.POST)
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "返回结构示例如下")
    })
    public ZtResBeanExList4Swagger showZtResBeanList4Swagger() {
        return new ZtResBeanExList4Swagger();
    }

}
