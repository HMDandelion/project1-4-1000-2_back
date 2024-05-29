package com.hmdandelion.project_1410002.Product.domian.entity;

import com.hmdandelion.project_1410002.Product.domian.type.ProductStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static com.hmdandelion.project_1410002.Product.domian.type.ProductStatus.PRODUCTION_DISCONTINUED;


@Entity
@Table(name="tbl_product")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Product {
    private static final ProductStatus IN_PRODUCTION = null;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productCode;
    private String productName;
    @CreatedDate
    private LocalDateTime launchDate;
    private Long price;
    private String unit;
    private LocalDateTime updated_at;
    @Enumerated(value= EnumType.STRING)
    private ProductStatus status = IN_PRODUCTION;

    public Product(String productName, Long price, String unit) {
        this.productName = productName;
        this.price = price;
        this.unit = unit;
    }

    public static Product of(String productName, Long price, String unit) {
        return new Product(
                productName,
                price,
                unit
        );
    }

    public void modify(String productName, Long price, String unit) {
        this.productName = productName;
        this.price = price;
        this.unit = unit;
    }

    public void updateStatus(Product product) {
        if(product.getStatus()== IN_PRODUCTION){
            this.status = PRODUCTION_DISCONTINUED;
        }else{
            this.status = IN_PRODUCTION;
        }
        this.updated_at = LocalDateTime.now();
    }
}
