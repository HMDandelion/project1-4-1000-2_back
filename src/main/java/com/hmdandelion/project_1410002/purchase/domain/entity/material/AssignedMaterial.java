package com.hmdandelion.project_1410002.purchase.domain.entity.material;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tbl_assigned_material")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class AssignedMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignedMaterialCode;
    private Long specCode;
    private Long clientCode;

    private AssignedMaterial(Long clientCode, Long specCode) {
        this.specCode = specCode;
        this.clientCode = clientCode;
    }

    public static AssignedMaterial of(Long clientCode, Long specCode) {
        return new AssignedMaterial(
                clientCode, specCode
        );
    }
}