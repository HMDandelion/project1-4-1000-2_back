package com.hmdandelion.project_1410002.inventory.domian.entity.material;

import jakarta.persistence.*;
import lombok.*;
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

    private MaterialSpec(Long specCode, String materialName, String remarks, String unit, SpecCategory category, int safetyStock, String specification) {
        this.specCode = specCode;
        this.materialName = materialName;
        this.remarks = remarks;
        this.unit = unit;
        this.category = category;
        this.safetyStock = safetyStock;
        this.specification = specification;
    }

    public static MaterialSpec of(Long specCode, String materialName, String remarks, String unit, SpecCategory foundCategory, Integer safetyQuantity, String specification) {
        return new MaterialSpec(
                specCode,
                materialName,
                remarks,
                unit,
                foundCategory,
                safetyQuantity,
                specification
        );
    }

}