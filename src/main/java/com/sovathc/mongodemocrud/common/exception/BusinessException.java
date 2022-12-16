package com.sovathc.mongodemocrud.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends Exception{
    private String code;

    public BusinessException(String code, String message)
    {
        super(message);
        this.code = code;
    }

    public BusinessException(String code, String message, Throwable throwable)
    {
        super(message, throwable);
        this.code = code;
    }

}
