package com.hmdandelion.project_1410002.inventory.dto.material.request;

import lombok.*;

@Getter
@ToString
@RequiredArgsConstructor
public class MaterialSpecCreateRequest {

    private final String materialName;
    private final long categoryCode;
    private final String unit;
    private final String specification;
    private final String remarks;
    private final Integer safetyStock;

}
