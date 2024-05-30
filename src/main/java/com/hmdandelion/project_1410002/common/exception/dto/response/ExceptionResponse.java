package com.hmdandelion.project_1410002.common.exception.dto.response;

import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExceptionResponse {

    private final int code;
    private final String message;

    public ExceptionResponse(ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }

    public static ExceptionResponse of(int code, String message) {
        return new ExceptionResponse(code, message);
    }
}