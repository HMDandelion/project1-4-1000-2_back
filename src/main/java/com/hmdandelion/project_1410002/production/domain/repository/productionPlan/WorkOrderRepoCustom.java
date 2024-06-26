package com.hmdandelion.project_1410002.production.domain.repository.productionPlan;

import com.hmdandelion.project_1410002.production.dto.response.WorkOrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WorkOrderRepoCustom {
    Page<WorkOrderResponse> getWorkOrders(Pageable pageable);

    Optional<WorkOrderResponse> getWorkOrder(Long workOrderCode);
}
