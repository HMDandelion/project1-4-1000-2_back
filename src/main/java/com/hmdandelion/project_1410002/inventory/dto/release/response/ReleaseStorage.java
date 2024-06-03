package com.hmdandelion.project_1410002.inventory.dto.release.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseStorage {
    private String productName;
    private Integer quantity;
    private List<String> warehouseName;
    private List<Long> releaseQuantity;

    public static ReleaseStorage of(String productName, Integer quantity, List<String> warehouseNames, List<Long> releaseQuantities) {
        return new ReleaseStorage(
                productName,
                quantity,
                warehouseNames,
                releaseQuantities
        );
    }
}
