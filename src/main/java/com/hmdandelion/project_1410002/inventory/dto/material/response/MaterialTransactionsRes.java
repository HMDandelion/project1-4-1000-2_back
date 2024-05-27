package com.hmdandelion.project_1410002.inventory.dto.material.response;

import com.hmdandelion.project_1410002.purchase.dto.OrderTransactionDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialTransactionsRes {
    private final Map<String, Double> monthTransactionMap;
    //TODO 마테리얼 트랜잭션 응답 작성중
    private final List<OrderTransactionDTO> orders;

    public static MaterialTransactionsRes of(Map<String, Double> monthTransactionMap, List<OrderTransactionDTO> orders) {
        return new MaterialTransactionsRes(
                monthTransactionMap,
                orders
        );
    }
}
