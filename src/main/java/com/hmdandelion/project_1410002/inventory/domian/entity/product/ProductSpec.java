package com.hmdandelion.project_1410002.inventory.domian.entity.product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tbl_product_spec")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductSpec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;
    private String color;
    private String type;
    private String size;
    private Long productCode;

    public ProductSpec(String color, String type, String size, Long productCode) {
        this.color = color;
        this.type = type;
        this.size = size;
        this.productCode = productCode;
    }

    public static ProductSpec of(String color, String type, String size, Long productCode) {
        return new ProductSpec(
                color,
                type,
                size,
                productCode
        );
    }

    public void modify(String color, String type, String size) {
        this.color=color;
        this.type=type;
        this.size=size;
    }
}
