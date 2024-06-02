package com.hmdandelion.project_1410002.production.dto.request;

import com.hmdandelion.project_1410002.production.domain.type.InspectionStatusType;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ProductionReportCreateRequest {


    // ProductionManagement fields
    private final LocalDateTime startAt;
    private final LocalDateTime completedAt;
    private final int totalProductionQuantity;
    private final String productionFile;
    private final ProductionStatusType productionStatus;
    private final InspectionStatusType inspectionStatus;

    // ProductionDetail fields
    private final LocalDateTime inspectionDate;
    private final int productionQuantity;
    private final int defectQuantity;
    private final int completelyQuantity;
    private final String productionMemo;
    private final ProductionStatusType productionStatusType;

    // defectDetail
    private final String defectReason;
    private final String defectStatus;
    private final String defectFile;

}