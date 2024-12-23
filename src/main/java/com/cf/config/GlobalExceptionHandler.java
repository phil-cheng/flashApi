package com.cf.config;

import com.cf.common.CommonResult;
import com.cf.common.ResponseCode;
import com.cf.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获系统异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public CommonResult<String> handleException(Exception e) {
        log.error("系统异常：{}", e.getMessage());
        return new CommonResult<>(ResponseCode.FAIL.getCode(), "系统错误，请联系管理员", null);
    }

    /**
     * 捕获自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    public CommonResult<String> handleBizException(BizException e) {
        log.error("业务异常：{}", e.getMessage());
        return new CommonResult<>(e.getCode(), e.getMessage(), null);
    }

    /**
     * 用于捕获@RequestBody类型参数触发校验规则抛出的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult<String> handleValidException(MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        if (!CollectionUtils.isEmpty(allErrors)) {
            for (ObjectError error : allErrors) {
                sb.append(error.getDefaultMessage()).append(";");
            }
        }
        return new CommonResult<>(ResponseCode.FAIL.getCode(),sb.toString(), null);
    }


    /**
     * 用于捕获@RequestParam/@PathVariable参数触发校验规则抛出的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public  CommonResult<String> handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder sb = new StringBuilder();
        Set<ConstraintViolation<?>> conSet = e.getConstraintViolations();
        for (ConstraintViolation<?> con : conSet) {
            String message = con.getMessage();
            sb.append(message).append(";");
        }
        return new CommonResult<>(ResponseCode.FAIL.getCode(),sb.toString(), null);
    }

}