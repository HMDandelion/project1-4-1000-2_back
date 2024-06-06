package com.hmdandelion.project_1410002.production.dto.request.updateProductionRequest;

import com.hmdandelion.project_1410002.production.domain.type.DefectStatusType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DefectDetailUpdateRequest {

    private final Long productionDetailCode;
    private final String defectReason;
    private final DefectStatusType defectStatus;
    private final String defectFile;
}