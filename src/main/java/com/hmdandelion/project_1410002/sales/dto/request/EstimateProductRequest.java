package com.hmdandelion.project_1410002.sales.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EstimateProductRequest {
    @NotNull
    public final Long estimateProductCode;
    @Min(value = 1)
    public final Integer quantity;
    @Min(value = 0)
    public final Integer price;
    @NotNull
    public final Long productCode;
}
