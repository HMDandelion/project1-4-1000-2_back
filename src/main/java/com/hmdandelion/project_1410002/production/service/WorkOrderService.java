package com.hmdandelion.project_1410002.production.service;

import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.service.ProductService;
import com.hmdandelion.project_1410002.production.domain.entity.Employee;
import com.hmdandelion.project_1410002.production.domain.entity.Line;
import com.hmdandelion.project_1410002.production.domain.entity.WorkOrder;
import com.hmdandelion.project_1410002.production.domain.repository.WorkOrderRepo;
import com.hmdandelion.project_1410002.production.domain.type.WorkOrderStatusType;
import com.hmdandelion.project_1410002.production.dto.request.WorkOrderCreateRequest;
import com.hmdandelion.project_1410002.production.dto.response.WorkOrderResponse;
import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;
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

    private final ProductService productService;

//    private final EmployeeService employeeService;
//
//    private final LineService lineService;

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


    public void workOrderRemove(Long workOrderCode) {

        workOrderRepo.deleteById(workOrderCode);
    }


    public void end(Long workOrderCode) {
        WorkOrder workOrder = workOrderRepo.findById(workOrderCode)
               .orElseThrow(() -> new NotFoundException(NOT_FOUND_WORK_ORDER));

        workOrder.end();
    }

    public Long workOrderSave(WorkOrderCreateRequest workOrderCreateRequest, WorkOrderStatusType workOrderStatusType) {
        final WorkOrder newWorkOrder = WorkOrder.of(
                workOrderCreateRequest.getWorkWrittenDate(),
                workOrderCreateRequest.getWorkOrderDate(),
                workOrderCreateRequest.getLineCode(),
                workOrderCreateRequest.getProductCode(),
                workOrderCreateRequest.getEmployeeCode(),
                workOrderCreateRequest.getOrderedQuantity(),
                workOrderStatusType
        );


        final WorkOrder workOrder = workOrderRepo.save(newWorkOrder);

        return workOrder.getWorkOrderCode();
    }

    public WorkOrderResponse getWorkOrderInSave() {

//        ProductDTO productDTO = productService.getProductById(workOrder.getProductCode());
//        EmployeeDTO employeeDTO = employeeService.getEmployeeById(workOrder.getEmployeeId());
//        LineDTO lineDTO = lineService.getLineById(workOrder.getLineId());
//
//        workOrder.setProduct(product);
//        workOrder.setEmployee(employee);
//        workOrder.setLine(line);

//        return workOrder;

        return null;
    }
}
