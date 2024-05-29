package com.hmdandelion.project_1410002.purchase.dto.material;

import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialSpecDTO;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.OrderSpec;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderSpecDTO {
    private Long orderSpecCode;
    private int orderQuantity;
    private int price;
    private MaterialSpecDTO materialSpec;

    public static OrderSpecDTO from(OrderSpec orderSpec) {
        OrderSpecDTO orderSpecDTO = new OrderSpecDTO();
        orderSpecDTO.setOrderSpecCode(orderSpec.getOrderSpecCode());
        orderSpecDTO.setOrderQuantity(orderSpec.getOrderQuantity());
        orderSpecDTO.setPrice(orderSpec.getPrice());

        orderSpecDTO.setMaterialSpec(MaterialSpecDTO.from(orderSpec.getMaterialSpec()));
        return orderSpecDTO;
    }
}
