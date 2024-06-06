package com.hmdandelion.project_1410002.production.dto.material;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class StockUsageDTO {
    private final Long stockUsageCode;
    private final int usedQuantity;
    private final String materialName;
}
