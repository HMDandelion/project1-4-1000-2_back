package com.hmdandelion.project_1410002.purchase.dto;

import com.hmdandelion.project_1410002.purchase.domain.entity.material.MaterialOrder;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.OrderSpec;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderTransactionDTO {
    private final long orderCode;
    private final String clientName;
    private final List<String> materials;
    private final String orderDate;
    private final int totalPrice;
    private final int price;

    public static OrderTransactionDTO of(MaterialOrder order, List<OrderSpec> orderSpecs,int price) {
        return new OrderTransactionDTO(
                order.getOrderCode(),
                order.getClientCode().toString(), //TODO 클라이언트명으로 변경 필요
                orderSpecs.stream().map(orderSpec -> orderSpec.getMaterialSpec().getMaterialName()).toList(),
                order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                calPrice(orderSpecs.stream().map(OrderSpec::getPrice).toList()),
                price
        );
    }

    private static int calPrice(List<Integer> prices) {
        int temp = 0;
        for (Integer i : prices) {
            temp += i;
        }
        return temp;
    }
}
