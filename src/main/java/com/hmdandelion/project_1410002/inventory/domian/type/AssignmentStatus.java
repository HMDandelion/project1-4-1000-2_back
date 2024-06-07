package com.hmdandelion.project_1410002.inventory.domian.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AssignmentStatus {
    NOT_ASSIGNED("not_assignment"),
    PARTIALLY_ASSIGNED("partially_assigned"),
    FULLY_ASSIGNED("fully_assigned");
    private final String value;
    AssignmentStatus(String value){this.value=value;}

    @JsonCreator
    public ProductStatus from(String value) {
        for(ProductStatus status : ProductStatus.values()) {
            if(status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() { return value; }
}
