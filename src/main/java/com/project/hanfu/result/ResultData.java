package com.project.hanfu.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 执行结果
 */
@ApiModel(value = "查询结果", parent = ResultBase.class)
public class ResultData<T> extends ResultBase {

    @ApiModelProperty("数据")
    private T returnData;// 数据

    public T getReturnData() {
        return returnData;
    }

    public void setReturnData(T returnData) {
        this.returnData = returnData;
    }
}
