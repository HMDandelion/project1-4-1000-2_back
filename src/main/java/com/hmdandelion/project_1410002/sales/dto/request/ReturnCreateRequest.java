package com.hmdandelion.project_1410002.sales.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.sales.domain.type.ManageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ReturnCreateRequest {
    private final Long orderCode;
    private final ManageType manageType;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate deadline;
    private final List<ReturnProductRequest> products;
}
