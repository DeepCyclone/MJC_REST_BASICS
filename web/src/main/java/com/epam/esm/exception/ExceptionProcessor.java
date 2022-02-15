package com.epam.esm.exception;

import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionProcessor {
    @ExceptionHandler(RepositoryException.class)
    @ResponseBody
    public ResponseEntity<ErrorDescriptor> objectNotFound(RepositoryException e){
        HttpStatus status = HTTPStatusDeterminer.determineHttpStatusByErrorCode(e.getErrorCode());
        return new ResponseEntity<>(new ErrorDescriptor(e.getErrorCode(),e.getErrorMsg()),status);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseEntity<ErrorDescriptor> objectNotFound(ServiceException e){
        HttpStatus status = HTTPStatusDeterminer.determineHttpStatusByErrorCode(e.getErrorCode());
        return new ResponseEntity<>(new ErrorDescriptor(e.getErrorCode(),e.getErrorMsg()),status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<?> validationExceptionHandler(MethodArgumentNotValidException e){
        StringBuilder builder = new StringBuilder();
        for(FieldError error:e.getFieldErrors()){
            builder.append(error.getDefaultMessage());
            builder.append("\n");
        }
        return new ResponseEntity<>(builder.toString(),HttpStatus.BAD_REQUEST);
    }

}
