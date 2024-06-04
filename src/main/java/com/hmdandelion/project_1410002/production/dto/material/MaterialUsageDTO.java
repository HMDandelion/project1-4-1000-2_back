package com.hmdandelion.project_1410002.production.dto.material;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialSpecDTO;
import com.hmdandelion.project_1410002.production.domain.type.MaterialUsageStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialUsageDTO {
    private final Long usageCode;
    private final String lineName;
    private final List<StockUsageDTO> stockUsages;
    private final LocalDateTime usageDatetime;
    @Enumerated(value = EnumType.STRING)
    private final MaterialUsageStatus status;
}
