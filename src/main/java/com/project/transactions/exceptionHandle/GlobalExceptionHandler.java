package com.project.transactions.exceptionHandle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.project.transactions.exception.CustomException;
import com.project.transactions.menu.StatusCode;
import com.project.transactions.result.ResultData;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResultData<Void> handleBindExceptionException(Exception ex) {

        ResultData<Void> resultData = new ResultData<>();
        resultData.setCode(StatusCode.ERROR);
        resultData.setSuccess(false);
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException manvex = (MethodArgumentNotValidException) ex;
            FieldError fieldError = manvex.getBindingResult().getFieldError();
            resultData.setMessage(fieldError.getDefaultMessage());
        } else {
            log.error("全局异常处理 {}", ex);
            ex.printStackTrace();
            resultData.setMessage("操作失败");
        }
        return resultData;
    }

    @ExceptionHandler({CustomException.class})
    @ResponseBody
    public ResultData<Void> handleBindExceptionCustomException(CustomException ex) {
        ResultData<Void> resultData = new ResultData<>();
        resultData.setCode(ex.getStatusCode());
        resultData.setSuccess(false);
        resultData.setMessage(ex.getMessage());

        return resultData;
    }


}

