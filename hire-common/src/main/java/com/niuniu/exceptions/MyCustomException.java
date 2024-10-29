package com.niuniu.exceptions;

import com.niuniu.result.ResponseStatusEnum;

public class MyCustomException extends RuntimeException{
    private ResponseStatusEnum responseStatusEnum;
    public MyCustomException(ResponseStatusEnum responseStatusEnum) {
        this.responseStatusEnum = responseStatusEnum;
    }

    public ResponseStatusEnum getResponseStatusEnum() {
        return responseStatusEnum;
    }

    public void setResponseStatusEnum(ResponseStatusEnum responseStatusEnum) {
        this.responseStatusEnum = responseStatusEnum;
    }
}
