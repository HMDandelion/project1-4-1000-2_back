package com.hmdandelion.project_1410002.production.domain.repository.production;

import com.hmdandelion.project_1410002.production.domain.entity.production.DefectDetail;
import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionDetail;
import com.hmdandelion.project_1410002.production.domain.type.DefectStatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefectDetailRepo extends JpaRepository<DefectDetail, Long> {
    List<DefectDetail> findByDefectCodeAndDefectStatus(Long defectCode, DefectStatusType defectStatus);

    List<DefectDetail> findByProductionDetailCodeAndDefectStatus(Long productionDetailCode, DefectStatusType defectStatus);

    List<DefectDetail> findDefectDetailByProductionDetailCode(Long productionDetailCode);
    List<DefectDetail> findByProductionDetail(ProductionDetail productionDetail);
}