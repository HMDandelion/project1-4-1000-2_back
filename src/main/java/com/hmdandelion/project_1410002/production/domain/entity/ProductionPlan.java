package com.hmdandelion.project_1410002.production.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_production_plan")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class ProductionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_code")
    private Long planCode;

    @CreatedDate
    @Column(name = "creation_at", nullable = false, updatable = false)
    private LocalDateTime creationAt;

    @Column(name = "start_at", nullable = false)
    private LocalDate startAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "end_at", nullable = false)
    private LocalDate endAt;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "plan_code" )
    private List<PlannedOrderList> plannedOrderList;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "plan_code" )
    private List<ProductionPlannedList> productionPlannedList;

    public ProductionPlan(LocalDate startAt, LocalDate endAt, List<ProductionPlannedList> productionPlanList, List<PlannedOrderList> plannedOrderList) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.productionPlannedList = productionPlanList;
        this.plannedOrderList = plannedOrderList;
    }


    public static ProductionPlan of(LocalDate startAt, LocalDate endAt, List<ProductionPlannedList> productionPlanList, List<PlannedOrderList> plannedOrderList) {
        return new ProductionPlan(startAt, endAt, productionPlanList, plannedOrderList);
    }

    public void planModify(LocalDate startAt, LocalDate endAt) {
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public void createPlan(List<ProductionPlannedList> productionPlanList, List<PlannedOrderList> plannedOrderList) {

    }
}
