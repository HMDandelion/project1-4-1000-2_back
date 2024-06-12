package com.hmdandelion.project_1410002.production.domain.type;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MaterialUsageStatus {
    READY("준비중"),
    DELIVERY("전달중"),
    COMPLETED("완료");

    private final String displayName;

    MaterialUsageStatus(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

}
