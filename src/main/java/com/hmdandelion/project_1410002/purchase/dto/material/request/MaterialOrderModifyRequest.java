package com.hmdandelion.project_1410002.purchase.dto.material.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.purchase.dto.material.OrderSpecCreateDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class MaterialOrderModifyRequest {
    @NotBlank
    private final Long orderCode;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate orderDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate deliveryDueDate;
    private final Long clientCode;
    private final Long employeeCode;
    private final Long planCode;
    private final List<OrderSpecCreateDTO> orderSpecList;
}
