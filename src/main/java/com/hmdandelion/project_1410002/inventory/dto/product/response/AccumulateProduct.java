package com.hmdandelion.project_1410002.inventory.dto.product.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class AccumulateProduct {
    private String productName;
    private Long accumulateQuantity;
    private Double ratio;



    public static AccumulateProduct of(String productName, Long sum,Double ratio) {
        return new AccumulateProduct(
                productName,
                sum,
                ratio
        );
    }
}
