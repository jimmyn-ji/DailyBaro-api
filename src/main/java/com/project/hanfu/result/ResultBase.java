package com.project.hanfu.result;

import com.project.hanfu.menu.StatusCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

@Data
@ApiModel(value="出参")
@Accessors(chain = true)
public class ResultBase implements Serializable {
    @ApiModelProperty(value="状态码",required = true,example = "1000")
    private StatusCode code;

    @ApiModelProperty(value = "消息", required = true, example = "success")
    private String message;

    @ApiModelProperty(value = "成功与否", required = true, example = "true")
    private Boolean success = true; // 成功与否


    private Object data;

}



