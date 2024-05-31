package com.hmdandelion.project_1410002.inventory.dto.product.request;

import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import lombok.*;

@RequiredArgsConstructor
@Getter
@ToString
public class ProductSpecRequest {
    private String color;
    private String type;
    private String size;
}
