package com.hmdandelion.project_1410002.purchase.domain.entity.material;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "tbl_material_order")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class MaterialOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderCode;
}
