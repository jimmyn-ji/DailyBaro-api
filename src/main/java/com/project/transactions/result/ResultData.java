package com.project.transactions.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.transactions.menu.StatusCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
public class ResultData<T> implements Serializable {
    @ApiModelProperty(value="状态码")
    @JsonProperty("code")
    private StatusCode code;

    @ApiModelProperty(value = "返回信息")
    private String message;

    @ApiModelProperty(value = "成功与否")
    private Boolean success = true; // 成功与否

    @ApiModelProperty(value = "具体数据")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public StatusCode getCode() {
        return code;
    }

    public ResultData<T> setCode(StatusCode code) {
        this.code = code;
        return this;
    }

    @JsonProperty("code")
    public Integer getCodeValue() {
        return code.getCode();
    }

    public String getMessage() {
        return message;
    }

    public ResultData<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public ResultData<T> setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResultData<T> setData(T data) {
        this.data = data;
        return this;
    }
}