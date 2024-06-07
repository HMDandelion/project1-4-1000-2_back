package com.hmdandelion.project_1410002.inventory.dto.stock.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StorageStockDTO {
    private String warehouseName;
    private Long initialQuantity;
    private String productName;
    private Long sum;
    private Long initialStockQuantity;

    public static StorageStockDTO of(String name, Long initialQuantity, String productName, Long sum, Long initialStockQuantity) {
        return new StorageStockDTO(
                name,
                initialQuantity,
                productName,
                sum,
                initialStockQuantity
        );
    }
}
