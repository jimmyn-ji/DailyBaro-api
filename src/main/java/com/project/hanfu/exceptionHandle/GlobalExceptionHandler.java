/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: GlobalExceptionHandler
 * Author:   HIAPAD
 * Date:     2020/3/10 15:19
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.project.hanfu.exceptionHandle;

import com.project.hanfu.exception.CustomException;
import com.project.hanfu.menu.StatusCode;
import com.project.hanfu.result.ResultBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author HIAPAD
 * @create 2020/3/10
 * @since 1.0.0
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResultBase handleBindExceptionException(Exception ex) {

        ResultBase resultBase = new ResultBase();
        resultBase.setCode(StatusCode.ERROR);
        resultBase.setSuccess(false);
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException manvex = (MethodArgumentNotValidException) ex;
            FieldError fieldError = manvex.getBindingResult().getFieldError();
            resultBase.setMessage(fieldError.getDefaultMessage());
        } else {
            log.error("全局异常处理 {}", ex);
            ex.printStackTrace();
            resultBase.setMessage("操作失败");
        }
        return resultBase;
    }


    @ExceptionHandler({CustomException.class})
    @ResponseBody
    public ResultBase handleBindExceptionCustomException(CustomException ex) {

        ResultBase resultBase = new ResultBase();
        resultBase.setCode(ex.getStatusCode());
        resultBase.setSuccess(false);
        resultBase.setMessage(ex.getMessage());

        return resultBase;
    }


}

