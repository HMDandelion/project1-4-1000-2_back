package com.hmdandelion.project_1410002.common.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    NOT_FOUND_PRODUCT_CODE(3000, "상품 코드에 해당하는 상품이 존재하지 않습니다."),

    ALREADY_EXIST_PRODUCTION_PLAN(7700, "해당 생산 계획의 기간이 현재 생산 계획과 겹칩니다.");

    private final int code;
    private final String message;
}
