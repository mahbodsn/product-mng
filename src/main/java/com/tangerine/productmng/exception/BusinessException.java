package com.tangerine.productmng.exception;

import lombok.Getter;

@Getter
public class BusinessException extends Exception {

    private final Integer code;

    private final String message;

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

}
