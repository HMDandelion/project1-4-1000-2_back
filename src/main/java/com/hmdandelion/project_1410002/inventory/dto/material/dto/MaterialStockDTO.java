package com.hmdandelion.project_1410002.inventory.dto.material.dto;

import lombok.*;

@Getter @Setter @ToString
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MaterialStockDTO {
    protected final Long stockCode;
    protected final int actualQuantity;
    protected final String unit;
    protected final String storageDate;

}
