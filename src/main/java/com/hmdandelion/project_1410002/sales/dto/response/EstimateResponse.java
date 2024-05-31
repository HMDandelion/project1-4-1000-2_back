package com.hmdandelion.project_1410002.sales.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.sales.domain.type.EstimateStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class EstimateResponse {
    private final Long estimateCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedAt;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate deadline;
    private final String clientName;
    private final EstimateStatus status;
    private final Boolean isOrdered;
    private final List<EstimateProductResponse> products;
}
