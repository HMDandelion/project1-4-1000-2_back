package com.hmdandelion.project_1410002.purchase.dto.material.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.production.domain.entity.ProductionPlan;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.MaterialOrder;
import com.hmdandelion.project_1410002.purchase.dto.material.OrderSpecDTO;
import com.hmdandelion.project_1410002.purchase.model.MaterialOrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class MaterialOrderResponse {
    private final String planName;
    private final String orderDate;
    private final String deliveryDueDate;
    private final String clientName;
    @Enumerated(EnumType.STRING)
    private final MaterialOrderStatus status;
    private final String remarks;
    private final String employeeName;
    private final String dpName;
    private final String phone;
    private final String arrivalDatetime;
    private final String warehouseName;
    private final List<OrderSpecDTO> orderSpecList;

//    public static MaterialOrderResponse from(
//            ProductionPlan plan, MaterialOrder order,
//    )
}
