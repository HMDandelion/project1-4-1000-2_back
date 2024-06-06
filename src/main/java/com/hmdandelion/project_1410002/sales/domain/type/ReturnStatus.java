package com.hmdandelion.project_1410002.sales.domain.type;

public enum ReturnStatus {
    AWAITING_INSPECTION,    // 검수 대기
    UNDER_INSPECTION,       // 검수 중
    INSPECTION_COMPLETED,   // 검수 완료
    CANCELED                // 반품 취소
}
