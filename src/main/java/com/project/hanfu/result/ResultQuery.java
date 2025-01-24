package com.project.hanfu.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 查询结果
 */
@ApiModel(value = "出参")
public class ResultQuery <T> extends ResultBase{

    @ApiModelProperty(value = "总条数", required = true, example = "12")
    private Integer totalItems; // 总条数

    @ApiModelProperty(value = "数据", required = true)
    private List<T> data;

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
