package com.hmdandelion.project_1410002.inventory.domian.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum StockType {
    PRODUCTS("products"),
    RE_INSPECTION("re_inspection");
    private final String value;
    StockType(String value){this.value=value;}

    @JsonCreator
    public StockType from(String value) {
        for(StockType type : StockType.values()) {
            if(type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() { return value; }
}
