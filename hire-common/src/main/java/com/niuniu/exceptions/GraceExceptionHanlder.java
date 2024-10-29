package com.niuniu.exceptions;

import com.niuniu.result.GraceJSONResult;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//等于切面处理异常
@ControllerAdvice
public class GraceExceptionHanlder {
    @ExceptionHandler(MyCustomException.class)
    @ResponseBody
    public GraceJSONResult returnMyCustomException(MyCustomException e){
       return GraceJSONResult.exception(e.getResponseStatusEnum());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public GraceJSONResult returnNotvalidException(MethodArgumentNotValidException e){
        BindingResult result = e.getBindingResult();
        Map<String, String> error=getError(result);
        return GraceJSONResult.errorMap(error);
    }
    public Map<String, String> getError(BindingResult result){
        Map<String, String> map=new HashMap<>();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for( FieldError fe : fieldErrors){
            String field = fe.getField();
            String defaultMessage = fe.getDefaultMessage();
            map.put(field,defaultMessage);
        }
        return map;
    }
}
