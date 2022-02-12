package com.epam.esm.exception;

import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HandlerConfig extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RepositoryException.class)
    @ResponseBody
    public ResponseEntity<ErrorDescriptor> objectNotFound(RepositoryException e){
        return new ResponseEntity<>(new ErrorDescriptor(e.getErrorCode(),e.getErrorMsg()),e.obtainHttpStatusOfError());
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseEntity<ErrorDescriptor> objectNotFound(ServiceException e){
        return new ResponseEntity<>(new ErrorDescriptor(e.getErrorCode(),e.getErrorMsg()),e.obtainHttpStatusOfError());
    }


}
