package com.hmdandelion.project_1410002.common.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    NOT_FOUND_PRODUCT_CODE(3000, "상품 코드에 해당하는 상품이 존재하지 않습니다."),
    NO_CONTENTS_MATERIAL_STOCK(3000, "요구하신 조건에 맞는 원자재 재고가 없습니다."),
    NO_WAREHOUSE(3001,"창고 코드에 맞는 창고가 존재하지 않습니다."),
    NOT_FOUND_BOM_CODE(3002,"상품 코드에 맞는 BOM이 존재하지 않습니다." ),

    ALREADY_EXIST_PRODUCTION_PLAN(9999,"에러 떠서 임의로 넣어놓음"),
    NOT_FOUND_CATEGORY_CODE(1111,"에러떠서 임의로 넣어놓음"),
    BAD_REQUEST_NO_OPTIONS(2222,"에러떠서 임의로 넣어놓음"),
    NOT_FOUND_MATERIAL_NAME(3333,"에러떠서 임의로 넣어놓음"),
    NOT_FOUND_STOCK_CODE(4444,"에러떠서 임의로 넣어놓음"),
    NOT_FOUND_WAREHOUSE_CODE(5555,"에러떠서 임의로 넣어놓음"),
    NOT_FOUND_SPEC_CODE(6666,"에러떠서 임의로 넣어놓음"),
    NOT_FOUND_CLIENT_CODE(7777,"에러떠서 임의로 넣어놓음"),
    NOT_FOUND_ESTIMATE_CODE(8888,"에러떠서 임의로 넣어놓음"),
    NOT_FOUND_ESTIMATE_PRODUCT_CODE(9191,"에러떠서 임의로 넣어놓음");
    private final int code;
    private final String message;
}
