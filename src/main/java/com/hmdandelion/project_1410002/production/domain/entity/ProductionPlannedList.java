package com.hmdandelion.project_1410002.production.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "tbl_production_plan_list")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class ProductionPlannedList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_list_code")
    private Long planListCode;

    @Column(name = "required_quantity")
    private String requiredQuantity;

    @Column(name = "planned_quantity")
    private String plannedQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_code", nullable = false)
    private ProductionPlan productionPlan;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_code", nullable = false)
//    private Product product; <- product 엔티티 필요





}
