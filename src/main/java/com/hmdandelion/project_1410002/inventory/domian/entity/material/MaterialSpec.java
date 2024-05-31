package com.hmdandelion.project_1410002.inventory.domian.entity.material;

import com.hmdandelion.project_1410002.inventory.dto.material.request.MaterialSpecModifyRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tbl_material_specification")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class MaterialSpec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long specCode;
    private String materialName;
    private String remarks;
    private String unit;
    @ManyToOne
    @JoinColumn(name = "spec_category_code")
    private SpecCategory category;
    private int safetyStock;
    private String specification;

    private MaterialSpec(String materialName, String remarks, String unit, SpecCategory category, int safetyStock, String specification) {
        this.materialName = materialName;
        this.remarks = remarks;
        this.unit = unit;
        this.category = category;
        this.safetyStock = safetyStock;
        this.specification = specification;
    }

    public static MaterialSpec of(String materialName, String remarks, String unit, SpecCategory foundCategory, Integer safetyStock, String specification) {
        return new MaterialSpec(
                materialName,
                remarks,
                unit,
                foundCategory,
                safetyStock,
                specification
        );
    }

    public void modifyFrom(MaterialSpecModifyRequest request, SpecCategory specCategory) {
        if (request.getMaterialName() != null && !request.getMaterialName().isBlank()) {
            this.materialName = request.getMaterialName();
        }
        if (request.getCategoryCode() != null) {
            this.category = specCategory;
        }
        if (request.getUnit() != null && !request.getUnit().isBlank()) {
            this.unit = request.getUnit();
        }
        if (request.getSpecification() != null && !request.getSpecification().isBlank()) {
            this.specification = request.getSpecification();
        }
        if (request.getRemarks() != null && !request.getRemarks().isBlank()) {
            this.remarks = request.getRemarks();
        }
        if (request.getSafetyStock() != null) {
            this.safetyStock = request.getSafetyStock();
        }
    }
}