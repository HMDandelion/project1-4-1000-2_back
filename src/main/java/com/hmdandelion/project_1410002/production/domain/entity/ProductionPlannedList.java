package com.hmdandelion.project_1410002.production.domain.entity;

import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @Column(name = "product_code")
    private Long productCode;

    @Column(name = "plan_code")
    private Long planCode;

    @Column(name = "required_quantity")
    private String requiredQuantity;

    @Column(name = "planned_quantity")
    private String plannedQuantity;

    @Column(name = "description")
    private String description;

//    @ManyToOne
//    @JoinColumn(name = "plan_code")
//    private ProductionPlan  productionPlan;

    public ProductionPlannedList(Long productCode, String plannedQuantity, String description, String requiredQuantity) {
        this.productCode = productCode;
        this.requiredQuantity = requiredQuantity;
        this.plannedQuantity = plannedQuantity;
        this.description = description;
    }
    public ProductionPlannedList(Long planListCode, String plannedQuantity, String description) {
        this.planListCode = planListCode;
        this.plannedQuantity = plannedQuantity;
        this.description = description;
    }

    public static ProductionPlannedList of(Long productCode, String plannedQuantity, String description, String requiredQuantity) {
        return new ProductionPlannedList(productCode, plannedQuantity, description, requiredQuantity);
    }


    public static ProductionPlannedList of(Long planListCode, String plannedQuantity, String description) {
        return new ProductionPlannedList(planListCode, plannedQuantity, description);
    }

    public void planModify(String plannedQuantity, String description) {
        this.plannedQuantity = plannedQuantity;
        this.description = description;
    }
}
