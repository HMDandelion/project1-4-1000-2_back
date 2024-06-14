package com.hmdandelion.project_1410002.sales.domain.type;

public enum OrderStatus {
    ORDER_RECEIVED, // 주문 접수
    IN_PRODUCTION,  // 생산 중
    WAIT_SHIPPING,  // 배송 준비
    SHIPPING,       // 배송 중
    COMPLETED,      // 최종 완료
    CANCELED,       // 취소됨
    RETURNED        // 반품됨
}
