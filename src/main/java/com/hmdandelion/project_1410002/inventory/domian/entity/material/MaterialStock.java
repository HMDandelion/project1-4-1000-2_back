package com.hmdandelion.project_1410002.inventory.domian.entity.material;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "tbl_material_stock")
@Entity(name = "InventoryMaterialStock") @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class MaterialStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockCode;
}
