package com.hmdandelion.project_1410002.sales.domain.type;

public enum ManageStatus {
    RETURN_RECEIVED,    // 반품 접수
    REFUNDED,           // 환불됨
    IN_PRODUCTION,      // 생산중
    INSPECTING,         // 검수중
    READY_FOR_SHIPMENT, // 출고 대기
    SHIPPED,            // 출고 완료
    COMPLETED,          // 완료됨
    CANCELED            // 취소됨

}
