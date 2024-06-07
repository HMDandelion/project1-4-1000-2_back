package com.hmdandelion.project_1410002.inventory.dto.stock.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDestroyDTO {
    private String productName;
    private Long destroyQuantity;
    private Double ratio;

    public static ProductDestroyDTO of(String productName, Long productSum, double ratio) {
        return new ProductDestroyDTO(
                productName,
                productSum,
                ratio
        );
    }
}
