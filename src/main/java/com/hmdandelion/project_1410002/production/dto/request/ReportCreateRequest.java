package com.hmdandelion.project_1410002.production.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Getter
@Setter
public class ReportCreateRequest {
    private ProductionManagementCreateRequest productionManagementCreateRequest;
    private List<ProductionDetailCreateRequest> productionDetailCreateRequest = new ArrayList<>();
    private List<DefectDetailCreateRequest> defectDetailCreateRequest = new ArrayList<>();
}