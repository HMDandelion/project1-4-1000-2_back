package com.hmdandelion.project_1410002.production.domain.type;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductionStatusType {
    PRODUCTION_COMPLETED("생산 완료"), // 생산 완료
//    ADDITIONAL_PRODUCTION("추가 생산"), // 추가 생산
    PRODUCTION_HOLD("생산 중지"),  // 생산 중지
    REGISTER_PRODUCTION("생산 등록"), // 생산 등록
    WAIT("대기중")     ;           // 대기중

    private final String displayName ;

    private ProductionStatusType (String displayName){
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName(){
        return displayName;
    }
}

// 프로그램이 스태틱은 시작해서 끝날 때 까지 있다