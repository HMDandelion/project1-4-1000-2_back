package com.hmdandelion.project_1410002.inventory.dto.stock.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDestroy {
    private String productName;
    private Long destroyQuantity;
    private Double ratio;

    public static ProductDestroy of(String productName, Long productSum, double ratio) {
        return new ProductDestroy(
                productName,
                productSum,
                ratio
        );
    }
}
