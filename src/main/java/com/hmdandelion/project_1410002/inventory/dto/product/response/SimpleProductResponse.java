package com.hmdandelion.project_1410002.inventory.dto.product.response;

import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SimpleProductResponse {
    private final Long productCode;
    private final String productName;
    private final Long price;

    public static SimpleProductResponse from(Product product) {
        return new SimpleProductResponse(
                product.getProductCode(),
                product.getProductName(),
                product.getPrice()
        );
    }
}
