package com.project.transactions.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 查询结果
 */
@ApiModel(value = "出参")
public class ResultQuery <T> extends ResultData{

    @ApiModelProperty(value = "总条数")
    private Integer totalItems; // 总条数

    @ApiModelProperty(value = "数据")
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
