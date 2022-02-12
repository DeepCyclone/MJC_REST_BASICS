package com.epam.esm.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class ServiceException extends RuntimeException{
    private final String errorCode;
    private final String errorMsg;

    public HttpStatus obtainHttpStatusOfError(){
        if(errorCode.startsWith("204")){
            return HttpStatus.NOT_FOUND;
        }
        if(errorCode.startsWith("409")){
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.OK;
    }
}
