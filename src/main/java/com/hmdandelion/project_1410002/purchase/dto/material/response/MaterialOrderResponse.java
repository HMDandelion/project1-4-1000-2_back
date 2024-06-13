package com.hmdandelion.project_1410002.purchase.dto.material.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmdandelion.project_1410002.production.domain.entity.ProductionPlan;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.MaterialOrder;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.OrderSpec;
import com.hmdandelion.project_1410002.purchase.dto.material.OrderSpecDTO;
import com.hmdandelion.project_1410002.purchase.model.MaterialOrderStatus;
import com.hmdandelion.project_1410002.employee.domain.entity.Employee;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.format.DateTimeFormatter;
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
    private final String employeeName;
    private final String dpName;
    private final String phone;
    private final String arrivalDatetime;
    private final List<OrderSpecDTO> orderSpecList;

    public static MaterialOrderResponse of(
            ProductionPlan plan, MaterialOrder order, Employee employee, String positionName, String departmentName, List<OrderSpec> orderSpecList
    ) {
        String planName = "지정된 생산계획 없음.";
        if (plan != null) {
         planName = plan.getCreationAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "_" + plan.getPlanCode();
        }
        String dpName = departmentName + " " + positionName;
        String str_arrivalDatetime = "";
        if (order.getArrivalDatetime() != null) {
            str_arrivalDatetime = order.getArrivalDatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        }
        return new MaterialOrderResponse(
                planName,
                order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                order.getDeliveryDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                order.getClient().getClientName(),
                order.getStatus(),
                employee.getEmployeeName(),
                dpName,
                employee.getPhone(),
                str_arrivalDatetime,
                orderSpecList.stream().map(OrderSpecDTO::from).toList()
        );

    }
}
