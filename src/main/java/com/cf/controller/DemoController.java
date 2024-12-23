package com.cf.controller;

import com.cf.common.CommonResult;
import com.cf.common.ResponseCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/demo")
@Api(tags = "示例类")
@Validated // 使validate对应注解生效 @NotNUll、@NotEmpty、@AssertTrue、@Size(max, min)等
public class DemoController {

    @ApiOperation(value = "测试请求")
    @GetMapping("/test")
    public CommonResult<String> test(@ApiParam(value="姓名", required=true) @NotNull(message = "姓名不能为空") String name) throws Exception {
        return new CommonResult<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), "success");
    }


}
