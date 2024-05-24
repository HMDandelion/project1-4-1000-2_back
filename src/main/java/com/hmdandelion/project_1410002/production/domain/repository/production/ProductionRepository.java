package com.hmdandelion.project_1410002.production.domain.repository.production;

import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionDetail;
import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionManagement;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ProductionRepository extends JpaRepository<ProductionManagement, Long> {
    Page<ProductionManagement> findByProductionStatus(Pageable pageable, ProductionStatusType productionStatusType);
    Page<ProductionManagement> findByProductionStatusNot(Pageable pageable, ProductionStatusType productionStatusType);
    Page<ProductionManagement> findByProductionStatusCodeAndProductionStatus(Pageable pageable, Long productionStatusCode, ProductionStatusType productionStatusType);
    Page<ProductionManagement> findByCompletedAt(Pageable pageable, LocalDateTime completedAt);



}
