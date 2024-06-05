package com.hmdandelion.project_1410002.production.domain.repository.productionPlan;


import com.hmdandelion.project_1410002.production.domain.entity.ProductionPlan;
import com.hmdandelion.project_1410002.production.dto.response.PlanListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;


public interface ProductionPlanRepo extends JpaRepository<ProductionPlan, Long> {

    @Query("SELECT new com.hmdandelion.project_1410002.production.dto.response.PlanListResponse(ppl, p, pp) " +
            "FROM ProductionPlan pp " +
            "LEFT JOIN ProductionPlannedList ppl ON pp.planCode = ppl.planCode" +
            " " +
            "LEFT JOIN Product p ON p.productCode = ppl.productCode " +
            "WHERE pp.startAt <= :endAt AND pp.endAt >= :startAt " +
            "ORDER BY (pp.endAt - CURRENT_TIMESTAMP)")
    Page<PlanListResponse> findPlanDetails(Pageable pageable, @Param("startAt") LocalDate startAt, @Param("endAt") LocalDate endAt);

    Optional<ProductionPlan> findByPlanCode(Long planCode);

    /* 생산 계획 기간 겹치지 않게 */
    @Query("SELECT COUNT(p) > 0 FROM ProductionPlan p WHERE :endAt >= p.startAt AND :startAt <= p.endAt")
    boolean existsByDateRange(@Param("startAt") LocalDate startAt, @Param("endAt") LocalDate endAt);
}
