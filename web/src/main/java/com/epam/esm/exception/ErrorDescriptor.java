package com.epam.esm.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ErrorDescriptor {
    private final String errorCode;
    private final String errorMsg;
}
