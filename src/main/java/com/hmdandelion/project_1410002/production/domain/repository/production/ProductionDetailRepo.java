package com.hmdandelion.project_1410002.production.domain.repository.production;

import com.hmdandelion.project_1410002.production.domain.entity.production.DefectDetail;
import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionDetail;
import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionManagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductionDetailRepo extends JpaRepository<ProductionDetail, Long> {
    /* 불량 상세조회*/
    Optional<ProductionDetail> findByProductionDetailCode(Long productionDetailCode);
}