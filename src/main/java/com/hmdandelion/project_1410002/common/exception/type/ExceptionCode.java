package com.hmdandelion.project_1410002.common.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    NOT_FOUND_PRODUCT_CODE(3000, "상품 코드에 해당하는 상품이 존재하지 않습니다."),
    NOT_FOUND_CLIENT_CODE(3100, "거래처 코드에 해당하는 거래처가 존재하지 않습니다."),
    NOT_FOUND_ESTIMATE_CODE(3200, "견적 코드에 해당하는 견적이 존재하지 않습니다."),
    NOT_FOUND_ESTIMATE_PRODUCT_CODE(3201, "견적 상품 코드에 해당하는 견적 상품이 존재하지 않습니다."),
    NOT_FOUND_CATEGORY_CODE(3400,"카테고리 코드에 해당하는 카테고리가 존재하지 않습니다." ),
    NOT_FOUND_STOCK_CODE(3401,"스톡 코드에 해당하는 스톡이 존재하지 않습니다." ),
    NOT_FOUND_MATERIAL_NAME(3402, "자재 이름에 해당하는 스톡이 존재하지 않습니다." ),
    NOT_FOUND_WAREHOUSE_CODE(3403,"창고 코드에 맞는 창고가 존재하지 않습니다" ),
    NOT_FOUND_SPEC_CODE(3404,"스펙 코드에 맞는 스펙이 존재하지 않습니다." ),
  
    NO_CONTENTS_MATERIAL_STOCK(4400, "요구하신 조건에 맞는 원자재 재고가 없습니다."),
 
    BAD_REQUEST_NO_OPTIONS(6400, "스펙 삭제에 필요한 정보를 제공하지 않았습니다." ),
  
    ALREADY_EXIST_PRODUCTION_PLAN(7700, "해당 생산 계획의 기간이 현재 생산 계획과 겹칩니다.");

    private final int code;
    private final String message;
}
