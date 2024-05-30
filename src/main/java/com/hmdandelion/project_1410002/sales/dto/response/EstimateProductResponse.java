package com.hmdandelion.project_1410002.sales.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EstimateProductResponse {
    public final Long productCode;
    public final String productName;
    public final Integer quantity;
    public final Integer price;
}
