package com.epam.esm.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class ServiceException extends RuntimeException{

    private final String errorCode;
    private final String errorMsg;

}
