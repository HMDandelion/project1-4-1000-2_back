package com.hmdandelion.project_1410002.sales.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.sales.domain.type.ManageType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ReturnCreateRequest {
    @NotNull
    private final Long orderCode;
    @NotNull
    private final ManageType manageType;
    @Future
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate deadline;
    @NotNull
    private final List<ReturnProductRequest> products;
}
