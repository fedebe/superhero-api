package com.api.superhero.exception.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.api.superhero.model.ErrorInfo;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorInfo> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {

        BindingResult result = e.getBindingResult();
        List<ObjectError> objectErrors = result.getAllErrors();

        List<String> errorMessages = new ArrayList<>();
        objectErrors.forEach( o -> errorMessages.add(o.getDefaultMessage()));
        
        ErrorInfo errorInfo = new ErrorInfo(errorMessages.toArray(String[]::new), HttpStatus.BAD_REQUEST.value(), request.getRequestURI());

        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

}
