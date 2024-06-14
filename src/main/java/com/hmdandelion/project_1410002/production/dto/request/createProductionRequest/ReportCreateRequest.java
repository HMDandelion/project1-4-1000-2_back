package com.hmdandelion.project_1410002.production.dto.request.createProductionRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Getter
@Setter
public class ReportCreateRequest {
//    private ProductionManagementCreateRequest productionManagementCreateRequest;
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
private final LocalDateTime startAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime completedAt;
    private final Integer totalProductionQuantity;
    private final String productionFile;
    private final ProductionStatusType productionStatus;


    private List<ProductionDetailCreateRequest> productionDetails;
}