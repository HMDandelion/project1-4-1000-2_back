package com.hmdandelion.project_1410002.inventory.domian.entity.material;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tbl_material_specification")
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private Category category;
    private int safetyStock;
    private String specification;

    public MaterialSpec(String materialName) {
        this.materialName = materialName;
    }
}