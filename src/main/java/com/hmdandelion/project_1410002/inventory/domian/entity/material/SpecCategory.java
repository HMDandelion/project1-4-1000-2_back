package com.hmdandelion.project_1410002.inventory.domian.entity.material;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tbl_spec_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class SpecCategory {

    @Id
    @Column(name = "spec_category_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryCode;
    @Column(name = "spec_category_name")
    private String categoryName;

    public SpecCategory(String categoryName) {
        this.categoryCode = null;
        this.categoryName = categoryName;
    }

    public static SpecCategory of(String newCategoryName) {
        return new SpecCategory(newCategoryName);
    }
}