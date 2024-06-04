package com.hmdandelion.project_1410002.production.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public class ProductionPlannedListRequest {

    private final String requiredQuantity;

    private final String plannedQuantity;

    private final String description;

    private final Long productCode;
}
