package com.hmdandelion.project_1410002.inventory.dto.material.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class MaterialStockModifyRequest {
    private final Long stockCode;
    @NotBlank
    private final Long warehouseCode;
    @Min(value = 1)
    private final Integer actualQuantity;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime inspectionDatetime; //추후 자재점검 기능을 위해서 남겨둠
    @NotBlank
    private final String modificationReason;
}
