package com.hmdandelion.project_1410002.production.domain.repository;


import com.hmdandelion.project_1410002.production.domain.entity.ProductionPlan;
import com.hmdandelion.project_1410002.production.dto.response.PlanListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ProductionPlanRepository extends JpaRepository <ProductionPlan, Long> {

    @Query("SELECT new com.hmdandelion.project_1410002.production.dto.response.PlanListResponse(p, ppp, pp) " +
            "FROM ProductionPlannedList p " +
            "JOIN ProductionPlan pp ON pp.planCode = p.planCode " +
            "JOIN Product ppp ON ppp.productCode = pp.planCode " +
            "ORDER BY (pp.endAt - CURRENT_TIMESTAMP)")
    Page<PlanListResponse> findPlanDetails(Pageable pageable);
}
