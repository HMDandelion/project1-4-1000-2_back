package com.hmdandelion.project_1410002.sales.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReturnProductRequest {
    @Min(value = 1)
    public final Integer quantity;
    @Min(value = 0)
    public final Integer refundPrice;
    @NotNull
    public final Long productCode;
}
