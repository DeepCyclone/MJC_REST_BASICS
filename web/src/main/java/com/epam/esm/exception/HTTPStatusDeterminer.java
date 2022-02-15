package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public class HTTPStatusDeterminer {
    public static HttpStatus determineHttpStatusByErrorCode(String errorCode){
        HttpStatus status = HttpStatus.OK;
        if(errorCode.startsWith("404")){
            status = HttpStatus.NOT_FOUND;
        }
        else if(errorCode.startsWith("409")){
            status = HttpStatus.CONFLICT;
        }
        return status;
    }
}
