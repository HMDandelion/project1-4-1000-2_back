package com.hmdandelion.project_1410002.production.dto.response.production;

import com.hmdandelion.project_1410002.production.domain.entity.production.DefectDetail;
import com.hmdandelion.project_1410002.production.domain.type.DefectStatusType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DefectDetailResponse {
    private final Long defectCode;
    private final Long productionDetailCode;
    private final String defectReason;
    private final DefectStatusType defectStatus;
    private final String defectFile;

    public static DefectDetailResponse from(DefectDetail defectDetail) {
        return new DefectDetailResponse(
                defectDetail.getDefectCode(),
                defectDetail.getProductionDetail().getProductionDetailCode(),
                defectDetail.getDefectReason(),
                defectDetail.getDefectStatus(),
                defectDetail.getDefectFile()
        );
    }
}