package com.hmdandelion.project_1410002.production.dto.request;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class WorkOrderCreateRequest {

    @CreatedDate
    private final LocalDate workWrittenDate;

    private final LocalDate workOrderDate;

    private final Long lineCode;

    private final Long employeeCode;

    private final Long productCode;

    private final Integer orderedQuantity; // 지시수량


}
