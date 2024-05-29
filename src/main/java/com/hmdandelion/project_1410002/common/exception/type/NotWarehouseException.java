package com.hmdandelion.project_1410002.common.exception.type;

import com.hmdandelion.project_1410002.common.exception.CustomException;
import lombok.Getter;

@Getter
public class NotWarehouseException extends CustomException {

    public NotWarehouseException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
