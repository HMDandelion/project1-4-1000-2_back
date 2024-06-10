package com.hmdandelion.project_1410002.production.domain.entity.production;

import com.hmdandelion.project_1410002.production.domain.type.DefectStatusType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Table(name = "tbl_defect_detail")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DefectDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long defectCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_detail_code")
    private ProductionDetail productionDetail;

    private String defectReason;
    @Enumerated(value = EnumType.STRING)
    private DefectStatusType defectStatus;
    private String defectFile;

    public DefectDetail(ProductionDetail newProductionDetail, String defectReason,
                        DefectStatusType defectStatus, String defectFile) {
        this.productionDetail = newProductionDetail;
        this.defectReason = defectReason;
        this.defectStatus = defectStatus;
        this.defectFile = defectFile;
    }

    public static DefectDetail of(ProductionDetail newProductionDetail, String defectReason,
                                  DefectStatusType defectStatus, String defectFile) {

        return new DefectDetail(
                newProductionDetail,
                defectReason,
                defectStatus,
                defectFile
        );
    }

    public void modifyDetail(String defectReason, DefectStatusType defectStatus, String defectFile) {
        this.defectReason = defectReason;
        this.defectStatus = defectStatus;
        this.defectFile = defectFile;
    }

}

