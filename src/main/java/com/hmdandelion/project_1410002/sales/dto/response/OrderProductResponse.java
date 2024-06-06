package com.hmdandelion.project_1410002.sales.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderProductResponse {
    private final Long productCode;
    private final String productName;
    private final Integer quantity;
    private final Integer price;
}
