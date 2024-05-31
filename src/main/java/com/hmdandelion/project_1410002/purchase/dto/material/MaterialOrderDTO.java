package com.hmdandelion.project_1410002.purchase.dto.material;

import com.hmdandelion.project_1410002.purchase.domain.entity.material.MaterialOrder;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.OrderSpec;
import com.hmdandelion.project_1410002.purchase.model.MaterialOrderStatus;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MaterialOrderDTO {

    private Long orderCode;
    private String orderDate;
    private String deliveryDueDate;
    private Long clientCode;
    private MaterialOrderStatus status;
    private Long employeeCode;
    private String arrivalDatetime;
    private Long planCode;

    private List<OrderSpecDTO> orderSpecList;

    public static MaterialOrderDTO of(MaterialOrder order, Map<Long, List<OrderSpec>> specsMap) {
        MaterialOrderDTO orderDTO = new MaterialOrderDTO();
        orderDTO.setOrderCode(order.getOrderCode());
        orderDTO.setOrderDate(order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        orderDTO.setDeliveryDueDate(order.getDeliveryDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        orderDTO.setClientCode(order.getClientCode());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setEmployeeCode(order.getEmployeeCode());
        if (order.getArrivalDatetime() != null) {
            orderDTO.setArrivalDatetime(order.getArrivalDatetime()
                                             .format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        }
        orderDTO.setPlanCode(order.getPlanCode());

        List<OrderSpecDTO> orderSpecList = specsMap.getOrDefault(order.getOrderCode(), List.of())
                                                   .stream()
                                                   .map(OrderSpecDTO::from)
                                                   .toList();
        orderDTO.setOrderSpecList(orderSpecList);
        return orderDTO;
    }

    public Double getAvgPriceBySpecCode(Long specCode) {
        double avg = 0d;
        for (OrderSpecDTO dto : orderSpecList) {
            if (dto.getMaterialSpec().getSpecCode() == specCode) {
                avg += dto.getPrice();
            }
        }
        return avg / orderSpecList.size();
    }
}


