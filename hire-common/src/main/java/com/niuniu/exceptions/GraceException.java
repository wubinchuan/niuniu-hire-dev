package com.niuniu.exceptions;

import com.niuniu.result.ResponseStatusEnum;

public class GraceException {
    public static void display(ResponseStatusEnum statusEnum){
        throw new MyCustomException(statusEnum);
    }
}
