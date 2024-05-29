package com.hmdandelion.project_1410002.sales.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.sales.domain.type.EstimateStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class EstimatesResponse {
    private final Long estimateCode;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDateTime createdAt;
    private final String clientName;
    private final Integer totalPrice;
    private final EstimateStatus status;
    private final Boolean isOrdered;


}
