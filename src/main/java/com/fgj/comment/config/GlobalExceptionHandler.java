package com.fgj.comment.config;

import com.fgj.comment.exception.NotFoundException;
import com.fgj.comment.exception.SystemInterruptException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e, HttpServletResponse response) throws IOException {
        e.printStackTrace();
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @ExceptionHandler(SystemInterruptException.class)
    public void handleSystemInterruptException(SystemInterruptException e,HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public void handleNotFoundException(NotFoundException e,HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValidException(MethodArgumentNotValidException e,HttpServletResponse response) throws IOException {
        BindingResult bindResult = e.getBindingResult();
        FieldError fieldError = bindResult.getFieldError();
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),fieldError.getDefaultMessage());
    }
}
