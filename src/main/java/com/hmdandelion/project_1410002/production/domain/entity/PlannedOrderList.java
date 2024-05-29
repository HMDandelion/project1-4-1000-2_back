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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_code", nullable = false)
    private ProductionPlan productionPlan;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_code", nullable = false)
//    private Order orderCode; <- order 엔티티 필요

}
