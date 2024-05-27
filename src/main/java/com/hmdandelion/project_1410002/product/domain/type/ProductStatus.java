package com.hmdandelion.project_1410002.product.domain.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProductStatus {
    IN_PRODUCTION("in_production"),
    PRODUCTION_DISCONTINUED("production_discontinued");
    private final String value;
    ProductStatus(String value){this.value=value;}

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
