package com.hmdandelion.project_1410002.production.domain.repository;

import com.hmdandelion.project_1410002.production.domain.entity.PlannedOrderList;
import com.hmdandelion.project_1410002.production.domain.entity.ProductionPlannedList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductionPlannedListRepo extends JpaRepository<ProductionPlannedList,Long> {

    List<ProductionPlannedList> findAllByPlanCode(Long planCode);
}
