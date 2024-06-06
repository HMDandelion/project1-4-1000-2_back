package com.hmdandelion.project_1410002.production.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tbl_planned_order_list")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class PlannedOrderList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planned_list_code")
    private Long plannedListCode;

    @Column(name = "plan_code")
    private Long planCode;

    @Column(name = "order_code")
    private Long orderCode;

    public PlannedOrderList(Long orderCode) {
        this.orderCode = orderCode;
    }

    public static PlannedOrderList of(Long orderCode) {
        return new PlannedOrderList(orderCode);
    }
}
