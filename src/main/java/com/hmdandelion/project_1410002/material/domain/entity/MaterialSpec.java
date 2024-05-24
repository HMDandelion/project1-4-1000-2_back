package com.hmdandelion.project_1410002.material.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tbl_material_specification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private SpecCategory specCategory;
    private int safetyStock;
    private String specification;
}