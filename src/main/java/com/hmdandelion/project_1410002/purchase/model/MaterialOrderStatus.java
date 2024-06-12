package com.hmdandelion.project_1410002.purchase.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MaterialOrderStatus {
    ORDER_COMPLETED("주문 됨"),
    DELIVERY_EXPECTED("배송 출발"),
    DELIVERY_COMPLETED("배송 완료"),
    ORDER_END("주문 완료");

    private final String displayName;

    MaterialOrderStatus(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }
}
