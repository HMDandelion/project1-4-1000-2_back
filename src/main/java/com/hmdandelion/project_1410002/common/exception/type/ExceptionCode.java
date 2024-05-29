package com.hmdandelion.project_1410002.common.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    NOT_FOUND_PRODUCT_CODE(3000, "상품 코드에 해당하는 상품이 존재하지 않습니다."),
    NOT_FOUND_CATEGORY_CODE(3400,"카테고리 코드에 해당하는 카테고리가 존재하지 않습니다." ),
    NOT_FOUND_STOCK_CODE(3401,"스톡 코드에 해당하는 스톡이 존재하지 않습니다." ),
    BAD_REQUEST_NO_OPTIONS(6400, "스펙 삭제에 필요한 정보를 제공하지 않았습니다." ),
    ALREADY_EXIST_PRODUCTION_PLAN(7700, "해당 생산 계획의 기간이 현재 생산 계획과 겹칩니다.");

    private final int code;
    private final String message;
}
