package com.hmdandelion.project_1410002.production.domain.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum WorkOrderStatusType {

    IN_PROGRESS("inProgress"),
    DONE("done");


    private final String value;

    WorkOrderStatusType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static WorkOrderStatusType from(String value) {
        for (WorkOrderStatusType status : WorkOrderStatusType.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }

    @JsonValue
    private String getValue() {
        return value;
    }
}