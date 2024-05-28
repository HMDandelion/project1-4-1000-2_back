package com.hmdandelion.project_1410002.common.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    NOT_FOUND_PRODUCT_CODE(3000, "상품 코드에 해당하는 상품이 존재하지 않습니다."),
    NO_CONTENTS_MATERIAL_STOCK(3000, "요구하신 조건에 맞는 원자재 재고가 없습니다."),
    NO_WAREHOUSE(4000,"창고 코드에 맞는 창고가 존재하지 않습니다.");

    private final int code;
    private final String message;
}
