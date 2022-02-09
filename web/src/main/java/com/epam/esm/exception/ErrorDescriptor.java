package com.epam.esm.exception;

public class ErrorDescriptor {
    private final String errorCode;
    private final String errorMsg;

    public ErrorDescriptor(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
