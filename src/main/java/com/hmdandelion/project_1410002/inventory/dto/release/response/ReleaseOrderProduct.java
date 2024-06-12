package com.hmdandelion.project_1410002.inventory.dto.release.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseOrderProduct {
    private String productName;
    private Integer quantity;

    public static ReleaseOrderProduct of(String productName, Integer quantity) {
       return new ReleaseOrderProduct(
                productName,
                quantity
        );
    }
}
