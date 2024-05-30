package com.hmdandelion.project_1410002.inventory.dto.stock.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StorageStock {
    private String warehouseName;
    private Long initialQuantity;
    private String productName;
    private Long sum;
    private Long initialStockQuantity;

    public static StorageStock of(String name, Long initialQuantity, String productName,Long sum,Long initialStockQuantity) {
        return new StorageStock(
                name,
                initialQuantity,
                productName,
                sum,
                initialStockQuantity
        );
    }
}
