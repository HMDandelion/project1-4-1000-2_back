package com.hmdandelion.project_1410002.sales.domain.entity.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "tbl_order_product")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductCode;
    private Integer quantity;
    private Integer price;
    @ManyToOne
    @JoinColumn(name = "order_code")
    private Order order;
    private Long productCode;
}
