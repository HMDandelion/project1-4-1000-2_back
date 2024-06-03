package com.hmdandelion.project_1410002.inventory.dto.material.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MaterialSpecModifyRequest {

    private final Long specCode;
    private final String materialName;
    private final Long categoryCode;
    private final String unit;
    private final String specification;
    private final String remarks;
    private final Integer safetyStock;
}
