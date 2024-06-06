package com.hmdandelion.project_1410002.sales.domain.entity.returns;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "tbl_return_product")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReturnProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long returnProductCode;
    private Integer quantity;
    private Integer refundPrice;
    @ManyToOne
    @JoinColumn(name = "return_code")
    private Return returnEntity;
    private Long productCode;
    private Integer defectiveQuantity;
    private Boolean inspectionStatus = false;

    private ReturnProduct(Integer quantity, Integer refundPrice, Return newReturn, Long productCode) {
        this.quantity = quantity;
        this.refundPrice = refundPrice;
        this.returnEntity = newReturn;
        this.productCode = productCode;
    }

    public static ReturnProduct of(Integer quantity, Integer refundPrice, Return newReturn, Long productCode) {
        return new ReturnProduct(
                quantity,
                refundPrice,
                newReturn,
                productCode
        );
    }
}
