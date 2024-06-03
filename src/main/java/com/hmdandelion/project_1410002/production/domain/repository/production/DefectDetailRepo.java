package com.hmdandelion.project_1410002.production.domain.repository.production;

import com.hmdandelion.project_1410002.production.domain.entity.production.DefectDetail;
import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefectDetailRepo extends JpaRepository<DefectDetail, Long> {
    List<DefectDetail> findByDefectCode(Long defectCode);


    List<DefectDetail> findByProductionDetail(ProductionDetail productionDetail);
}