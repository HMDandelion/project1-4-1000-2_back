package com.hmdandelion.project_1410002.inventory.domian.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ReleaseStatus {
    WAIT("wait"),SHIPPING("shipping"),DELIVERY_COMPLETED("delivery_completed");
    private final String value;
    ReleaseStatus(String value){this.value=value;}

    @JsonCreator
    public ReleaseStatus from(String value) {
        for(ReleaseStatus type : ReleaseStatus.values()) {
            if(type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() { return value; }
}
