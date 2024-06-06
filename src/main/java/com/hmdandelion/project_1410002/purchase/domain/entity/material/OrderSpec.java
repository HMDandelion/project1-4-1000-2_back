package com.hmdandelion.project_1410002.purchase.domain.entity.material;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import jakarta.persistence.*;
import jakarta.persistence.criteria.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tbl_order_spec")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class OrderSpec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderSpecCode;

    private int orderQuantity;
    private int price;
    private Long orderCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "spec_code")
    private MaterialSpec materialSpec;

    public OrderSpec(int orderQuantity, int price, Long orderCode, MaterialSpec materialSpec) {
        this.orderQuantity = orderQuantity;
        this.price = price;
        this.orderCode = orderCode;
        this.materialSpec = materialSpec;
    }

    public static OrderSpec of(Long orderCode, MaterialSpec spec, int orderQuantity, int price) {
        return new OrderSpec(
                orderQuantity,
                price,
                orderCode,
                spec
        );
    }
}