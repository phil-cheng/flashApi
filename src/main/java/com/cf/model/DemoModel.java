package com.cf.model;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 我要开证明模板替换内容
 */
@Data
public class DemoModel implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 姓名 */
    @NotNull(message = "姓名不能为空")
    @ApiParam(value = "姓名", required = true)
    private String name;

}
