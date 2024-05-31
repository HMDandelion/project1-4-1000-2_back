package com.hmdandelion.project_1410002.production.service;

import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.production.domain.entity.WorkOrder;
import com.hmdandelion.project_1410002.production.domain.repository.WorkOrderRepo;
import com.hmdandelion.project_1410002.production.dto.request.WorkOrderCreateRequest;
import com.hmdandelion.project_1410002.production.dto.response.WorkOrderResponse;
import com.hmdandelion.project_1410002.sales.dto.response.EstimateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hmdandelion.project_1410002.common.exception.type.ExceptionCode.NOT_FOUND_WORK_ORDER;
import static com.hmdandelion.project_1410002.production.domain.type.WorkOrderStatusType.DONE;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkOrderService {

    private final WorkOrderRepo workOrderRepo;

    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 10, Sort.by("workOrderCode").descending());
    }

    @Transactional(readOnly = true)
    public Page<WorkOrderResponse> getWorkOrders(Integer page) {
        Page<WorkOrderResponse> workOrders = workOrderRepo.getWorkOrders(getPageable(page));

        return workOrders;
    }

    @Transactional(readOnly = true)
    public WorkOrderResponse getWorkOrder(Long workOrderCode) {
        WorkOrderResponse workOrder = workOrderRepo.getWorkOrder(workOrderCode)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_ESTIMATE_CODE));
        return workOrder;
    }



    public Long workOrderSave(WorkOrderCreateRequest workOrderCreateRequest) {
        return null;
    }

    public void workOrderRemove(Long workOrderCode) {

        workOrderRepo.deleteById(workOrderCode);
    }


    public void end(Long workOrderCode) {
        WorkOrder workOrder = workOrderRepo.findById(workOrderCode)
               .orElseThrow(() -> new NotFoundException(NOT_FOUND_WORK_ORDER));

        workOrder.end();
    }

}
