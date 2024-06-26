package com.hmdandelion.project_1410002.production.domain.repository.production;

import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionDetail;
import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionManagement;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductionRepo extends JpaRepository<ProductionManagement, Long> {
    /* 생산 완료만 조회 */
    Page<ProductionManagement> findByProductionStatus(Pageable pageable, ProductionStatusType productionStatusType);


    /* ProductionStatusCode와 ProductionStatus로 조회 */
    Page<ProductionManagement> findByProductionStatusCodeAndProductionStatus(Pageable pageable, Long productionStatusCode, ProductionStatusType productionStatusType);

    /* 상세 조회 */
    Optional<ProductionManagement>findByProductionStatusCode(Long productionStatusCode);

}

//    /* 완료 날짜로 조회 */
//    Page<ProductionManagement> findByCompletedAtBetween(Pageable pageable, LocalDateTime startAt, LocalDateTime completedAt);
//
//    /* 작업 종료 날짜로 조회*/
//    Page<ProductionManagement> findByCompletedAt(Pageable pageable, LocalDateTime completedAt);
//
//    /*작업 시작 날짜로 조회 */
//    Page<ProductionManagement> findByStartAt(Pageable pageable, LocalDateTime startAt);
/* ProductionStatus 제외 조회 */
//    Page<ProductionManagement> findByProductionStatusNot(Pageable pageable, ProductionStatusType productionStatusType);
