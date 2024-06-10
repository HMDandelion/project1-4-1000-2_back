package com.hmdandelion.project_1410002.production.dto.request.updateProductionRequest;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportUpdateRequest {
    private ProductionManagementUpdateRequest productionManagementUpdateRequest;
    private List<ProductionDetailUpdateRequest> productionDetailUpdateRequest = new ArrayList<>();
    private List<DefectDetailUpdateRequest> defectDetailUpdateRequest = new ArrayList<>();
}