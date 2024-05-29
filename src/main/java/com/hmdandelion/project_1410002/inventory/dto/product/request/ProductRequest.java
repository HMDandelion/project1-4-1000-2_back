package com.hmdandelion.project_1410002.inventory.dto.product.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProductRequest {
    private final String productName;
    private final Long price;
    private final String unit;
}
