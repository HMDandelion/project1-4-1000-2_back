package com.hmdandelion.project_1410002.production.domain.repository.productionPlan;

import com.hmdandelion.project_1410002.production.domain.entity.WorkOrder;
import com.hmdandelion.project_1410002.production.domain.type.WorkOrderStatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WorkOrderRepo extends JpaRepository<WorkOrder, Long>, WorkOrderRepoCustom {
    Optional<WorkOrder> findByWorkOrderCodeAndCompletionStatus(Long workOrderCode, WorkOrderStatusType workOrderStatusType);

    Optional<WorkOrder> findByWorkOrderCode(Long workOrderCode);

    boolean existsByWorkOrderDateAndLineCode(LocalDate workOrderDate, Long lineCode);
}
