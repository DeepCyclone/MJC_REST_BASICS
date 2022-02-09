package com.epam.esm.exception;

import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HandlerConfig extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDescriptor objectNotFound(RepositoryException e){
        return new ErrorDescriptor(e.getErrorCode(),e.getErrorMsg());
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorDescriptor updateError(ServiceException e){
        return new ErrorDescriptor(e.getErrorCode(),e.getErrorMsg());
    }


}
