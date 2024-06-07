package com.hmdandelion.project_1410002.production.dto.material.response;

import com.hmdandelion.project_1410002.inventory.domian.entity.product.Bom;
import com.hmdandelion.project_1410002.inventory.dto.product.dto.BomDTO;
import com.hmdandelion.project_1410002.production.domain.entity.line.Line;
import com.hmdandelion.project_1410002.production.domain.entity.material.MaterialUsage;
import com.hmdandelion.project_1410002.production.domain.type.MaterialUsageStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialUsageResponse {


    private final Long usageCode;
    private final LocalDateTime usageDatetime;
    private final MaterialUsageStatus status;
    private final Long orderedQuantity;


    private final String employeeName;
    private final String positionName;
    private final String departmentName;
    private final String phone;


    private final String lineName;

    private final List<BomDTO> boms;

    public static MaterialUsageResponse of(MaterialUsage usage,
                                           String employeeName,
                                           String positionName,
                                           String departmentName,
                                           String phone,
                                           String lineName,
                                           List<Bom> boms) {
        return new MaterialUsageResponse(
                usage.getUsageCode(),
                usage.getUsageDatetime(),
                usage.getStatus(),
                (long) usage.getWorkOrder().getOrderedQuantity(),
                employeeName,
                positionName,
                departmentName,
                phone,
                lineName,
                boms.stream().map(BomDTO::from).toList()
        );
    }

}
