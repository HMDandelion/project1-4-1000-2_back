package com.hmdandelion.project_1410002.production.dto.material.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StockUsageCreateRequest {
    @NotBlank
    private final Long stockCode;
    @NotBlank
    private final Long usageCode;
    @Min(value = 1)
    private final Long usedQuantity;
}
