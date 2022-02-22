package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionProcessor  {

    private static final int ERROR_CODE_START = 0;
    private static final int ERROR_CODE_END = 3;
    @ExceptionHandler(RepositoryException.class)
    @ResponseBody
    public ResponseEntity<ErrorDescriptor> objectNotFound(RepositoryException e){
        HttpStatus status = HttpStatus.resolve(Integer.parseInt(e.getErrorCode().substring(ERROR_CODE_START,ERROR_CODE_END)));
        return new ResponseEntity<>(new ErrorDescriptor(e.getErrorCode(),e.getErrorMsg()),status);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseEntity<ErrorDescriptor> objectNotFound(ServiceException e){
        HttpStatus status = HttpStatus.resolve(Integer.parseInt(e.getErrorCode().substring(ERROR_CODE_START,ERROR_CODE_END)));
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
