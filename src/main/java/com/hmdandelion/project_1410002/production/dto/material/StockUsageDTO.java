package com.hmdandelion.project_1410002.production.dto.material;

import com.hmdandelion.project_1410002.production.domain.entity.material.StockUsage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class StockUsageDTO {
    private final Long stockUsageCode;
    private final boolean transmissionStatus;
    private final int usedQuantity;
    private final String materialName;

    public static StockUsageDTO from(StockUsage stockUsage,String materialName) {
        return new StockUsageDTO(
                stockUsage.getStockUsageCode(),
                stockUsage.isTransmissionStatus(),
                stockUsage.getUsedQuantity(),
                materialName
        );
    }
}
