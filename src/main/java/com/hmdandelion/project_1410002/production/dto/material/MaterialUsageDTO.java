package com.hmdandelion.project_1410002.production.dto.material;

import com.hmdandelion.project_1410002.production.domain.entity.material.MaterialUsage;
import com.hmdandelion.project_1410002.production.domain.type.MaterialUsageStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialUsageDTO {
    private final Long usageCode;
    private final Long lineCode;
    private String lineName;
    private final List<StockUsageDTO> stockUsages;
    private final LocalDateTime usageDatetime;
    @Enumerated(value = EnumType.STRING)
    private final MaterialUsageStatus status;
    private final Long workOrderCode;

    public static MaterialUsageDTO from(MaterialUsage usage) {
        return new MaterialUsageDTO(
                usage.getUsageCode(),
                usage.getWorkOrder().getLineCode(),
                new ArrayList<>(),
                usage.getUsageDatetime(),
                usage.getStatus(),
                usage.getWorkOrder().getWorkOrderCode()
        );
    }

    public void addLineName(String lineName) {
        this.lineName = lineName;
    }

    public void addStockUsages(List<StockUsageDTO> list) {
        this.stockUsages.addAll(list);
    }
}
