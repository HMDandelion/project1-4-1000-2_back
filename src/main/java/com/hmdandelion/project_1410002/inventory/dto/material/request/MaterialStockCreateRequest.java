package com.hmdandelion.project_1410002.inventory.dto.material.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class MaterialStockCreateRequest {
    @NotBlank
    private final String division;
    @NotBlank
    private final Long specCode;
    @NotBlank
    private final Long warehouseCode;
    @Min(value = 1)
    private final int incomingQuantity;
    @Min(value = 1)
    private final int actualQuantity;
    private final LocalDateTime storageDatetime;
    private final String remarks;
    private final LocalDateTime inspectionDatetime;
    private final LocalDateTime modificationDatetime;
    private final String modificationReason;
    private final Long orderCode;
}
