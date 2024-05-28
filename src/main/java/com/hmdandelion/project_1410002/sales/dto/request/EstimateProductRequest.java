package com.hmdandelion.project_1410002.sales.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EstimateProductRequest {
    public final Integer quantity;
    public final Integer price;
    public final Long productCode;
}
