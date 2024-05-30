package com.hmdandelion.project_1410002.common.exception;

import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import lombok.Getter;

@Getter
public class NoContentsException extends CustomException {
    public NoContentsException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
