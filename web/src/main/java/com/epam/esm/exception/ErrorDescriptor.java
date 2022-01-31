package com.epam.esm.exception;

public class ErrorDescriptor {
    private final int errorCode;
    private final String errorMsg;

    public ErrorDescriptor(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
