package com.hmdandelion.project_1410002.production.dto.request;

import com.hmdandelion.project_1410002.production.domain.type.DefectStatusType;
import com.hmdandelion.project_1410002.production.domain.type.InspectionStatusType;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ProductionManagementCreateRequest {

    private final LocalDateTime startAt;
    private final LocalDateTime completedAt;
    private final Integer totalProductionQuantity;
    private final String productionFile;
    private final ProductionStatusType productionStatus;
}