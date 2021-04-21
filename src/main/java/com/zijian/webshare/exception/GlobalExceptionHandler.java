package com.zijian.webshare.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * 处理参数校验错误
     * @param e 参数校验错误
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
    }

    /**
     * 处理资源不存在的错误（用户不存在、链接不存在）
     * @param e
     * @return
     */
    @ExceptionHandler(ResourceEmptyException.class)
    public ResponseEntity<String> handleEmptyException(ResourceEmptyException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
    }

    /**
     * 处理请求错误 (用户已存在)
     * @param e
     * @return
     */
    @ExceptionHandler(RequestException.class)
    public ResponseEntity<String> handleRequestException(RequestException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
