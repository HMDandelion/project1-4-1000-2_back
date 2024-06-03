package com.hmdandelion.project_1410002.sales.domain.entity.estimate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "tbl_estimate_product")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EstimateProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long estimateProductCode;
    private Integer quantity;
    private Integer price;
    @ManyToOne
    @JoinColumn(name = "estimate_code")
    private Estimate estimate;
    private Long productCode;

    private EstimateProduct(Integer quantity, Integer price, Long productCode, Estimate newEstimate) {
        this.quantity = quantity;
        this.price = price;
        this.productCode = productCode;
        this.estimate = newEstimate;
    }

    public static EstimateProduct of(Integer quantity, Integer price, Long productCode, Estimate newEstimate) {
        return new EstimateProduct(
                quantity,
                price,
                productCode,
                newEstimate
        );
    }

    public void modify(Integer quantity, Integer price, Long productCode) {
        this.quantity = quantity;
        this.price = price;
        this.productCode = productCode;
    }

}
