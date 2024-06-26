package com.hmdandelion.project_1410002.purchase.dto.material.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.purchase.dto.material.OrderSpecCreateDTO;
import com.hmdandelion.project_1410002.purchase.dto.material.OrderSpecDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class MaterialOrderCreateRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate orderDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate deliveryDueDate;
    private final Long clientCode;
    @Setter
    private Long employeeCode;
    private final Long planCode;
    private final List<OrderSpecCreateDTO> orderSpecList;

}
