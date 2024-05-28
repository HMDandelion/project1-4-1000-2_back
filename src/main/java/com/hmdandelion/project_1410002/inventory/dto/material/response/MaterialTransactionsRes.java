package com.hmdandelion.project_1410002.inventory.dto.material.response;

import com.hmdandelion.project_1410002.purchase.dto.material.MaterialOrderDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialTransactionsRes {
    private final Map<String, Double> monthTransactionMap;
    private final List<MaterialOrderDTO> orders;

    public static MaterialTransactionsRes of(Map<String, Double> monthTransactionMap, List<MaterialOrderDTO> orders) {
        return new MaterialTransactionsRes(
                monthTransactionMap,
                orders
        );
    }
}
