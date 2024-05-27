package com.hmdandelion.project_1410002.purchase.domain.entity.material;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "order_code")
    private MaterialOrder materialOrder;

    @ManyToOne
    @JoinColumn(name = "spec_code")
    private MaterialSpec materialSpec;
}