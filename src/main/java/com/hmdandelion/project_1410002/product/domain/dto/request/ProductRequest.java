package com.hmdandelion.project_1410002.product.domain.dto.request;

import com.hmdandelion.project_1410002.product.domain.type.ProductStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
@RequiredArgsConstructor
@Getter
public class ProductRequest {
    private final String productName;
    private final Long price;
    private final String unit;
}
