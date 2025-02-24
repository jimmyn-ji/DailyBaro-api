package com.project.transactions.exception;


import com.project.transactions.menu.StatusCode;

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
