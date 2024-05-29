package com.hmdandelion.project_1410002.inventory.dto.material.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class SaveMaterialStockRequest {

    private final Long stockCode;
    private final String division;
    private final Long specCode;
    private final Long warehouseCode;
    private final int incomingQuantity;
    private final int actualQuantity;
    private final LocalDateTime storageDatetime;
    private final String remarks;
    private final LocalDateTime inspectionDatetime;
    @LastModifiedDate
    private final LocalDateTime modificationDatetime;
    private final String modificationReason;
    private final int orderCode;
}
