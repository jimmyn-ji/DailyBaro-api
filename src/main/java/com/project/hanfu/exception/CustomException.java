package com.project.hanfu.exception;

import com.project.hanfu.menu.StatusCode;

/**
 * 系统自定义异常类，针对预期异常，需要在程序中抛出此类异常
 */
@SuppressWarnings("serial")
public class CustomException extends RuntimeException {
    private StatusCode statusCode;
    private String message;

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CustomException(StatusCode statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public CustomException(String message) {
        this.message = message;
        this.statusCode = StatusCode.ERROR;
    }
}
