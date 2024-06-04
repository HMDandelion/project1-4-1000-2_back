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
    NOT_FOUND_ORDER_CODE(3300, "주문 코드에 해당하는 주문이 존재하지 않습니다."),
    NOT_FOUND_RETURN_CODE(3301, "반품 코드에 해당하는 반품이 존재하지 않습니다."),
    NOT_FOUND_CATEGORY_CODE(3400,"카테고리 코드에 해당하는 카테고리가 존재하지 않습니다." ),
    NOT_FOUND_STOCK_CODE(3401,"스톡 코드에 해당하는 스톡이 존재하지 않습니다." ),
    NOT_FOUND_MATERIAL_NAME(3402, "자재 이름에 해당하는 스톡이 존재하지 않습니다." ),
    NOT_FOUND_WAREHOUSE_CODE(3403,"창고 코드에 맞는 창고가 존재하지 않습니다" ),
    NOT_FOUND_SPEC_CODE(3404,"스펙 코드에 맞는 스펙이 존재하지 않습니다." ),
    NOT_FOUND_BOM_CODE(3501,"BOM 코드에 맞는 BOM이 존재하지 않습니다."),
    NOT_FOUND_STORAGE_CODE(3502,"저장 이력 코드가 존재하지 않습니다."),

    NO_CONTENTS_MATERIAL_STOCK(4400, "요구하신 조건에 맞는 원자재 재고가 없습니다."),

    BAD_REQUEST_ORDER_EXIST_CLIENT(6100, "주문건이 존재하는 거래처는 삭제할 수 없습니다."),
    BAD_REQUEST_ORDERED_ESTIMATE(6200, "주문이 진행된 견적은 삭제할 수 없습니다."),
    BAD_REQUEST_DEADLINE_PASSED(6201, "마감일자가 지난 견적은 주문으로 전환할 수 없습니다."),
    BAD_REQUEST_NO_OPTIONS(6400, "스펙 삭제에 필요한 정보를 제공하지 않았습니다." ),
    BAD_REQUEST_MORE_QUANTITY(6500,"보관 중인 재고가 재고 수량보다 큽니다."),
    BAD_REQUEST_DELETED_STOCK(6501,"삭제 된 재고 정보입니다."),
    BAD_REQUEST_DESTROY_QUANTITY(6502,"파손 수량은 초기 수량보다 클 수 없습니다."),

    NOT_FOUND_PRODUCTION_CODE(3000, "상품 코드에 해당하는 생산 보고서가 존재하지 않습니다."),

    NOT_FOUND_PRODUCTION_DETAIL(30000 ,"상품의 불량 처리에 대한 정보가 존재하지 않습니다."),

    ALREADY_EXIST_PRODUCTION_PLAN(7700, "해당 생산 계획의 기간이 현재 생산 계획과 겹칩니다."),

    FAIL_LOGIN(9900, "로그인에 실패하였습니다."),
    NOT_FOUND_REFRESH_TOKEN(9901, "리프레시 토큰이 유효하지 않습니다."),
    UNAUTHORIZED(9902, "인증되지 않은 요청입니다."),
    ACCESS_DENIED(9903, "허가되지 않은 요청입니다."),;


    private final int code;
    private final String message;
}
