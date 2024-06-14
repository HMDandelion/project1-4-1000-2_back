package com.hmdandelion.project_1410002.production.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.production.domain.type.WorkOrderStatusType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class WorkOrderResponse {

    private final Long workOrderCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate workOrderDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate workWrittenDate;

    private final int orderedQuantity;

    private final WorkOrderStatusType completionStatus;

    private final String productName;

    private final String employeeName;

    private final String lineName;
}
