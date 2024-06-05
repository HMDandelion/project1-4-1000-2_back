package com.hmdandelion.project_1410002.production.service;

import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.service.ProductService;
import com.hmdandelion.project_1410002.production.domain.entity.WorkOrder;
import com.hmdandelion.project_1410002.production.domain.repository.productionPlan.WorkOrderRepo;
import com.hmdandelion.project_1410002.production.domain.type.WorkOrderStatusType;
import com.hmdandelion.project_1410002.production.dto.request.WorkOrderCreateRequest;
import com.hmdandelion.project_1410002.production.dto.request.WorkOrderUpdateRequest;
import com.hmdandelion.project_1410002.production.dto.response.WorkOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.hmdandelion.project_1410002.common.exception.type.ExceptionCode.ALREADY_EXIST_WORK_ORDER;
import static com.hmdandelion.project_1410002.common.exception.type.ExceptionCode.NOT_FOUND_WORK_ORDER;


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
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_WORK_ORDER));
        return workOrder;
    }

    public Long workOrderSave(WorkOrderCreateRequest workOrderCreateRequest) {
        LocalDate workOrderDate = workOrderCreateRequest.getWorkOrderDate();

        // 이미 등록된 작업인지 확인
        if (workOrderRepo.existsByWorkOrderDateAndLineCode(workOrderDate, workOrderCreateRequest.getLineCode())) {
            throw new NotFoundException(ALREADY_EXIST_WORK_ORDER);
        }
        final WorkOrder newWorkOrder = WorkOrder.of(
                workOrderCreateRequest.getWorkWrittenDate(),
                workOrderCreateRequest.getWorkOrderDate(),
                workOrderCreateRequest.getLineCode(),
                workOrderCreateRequest.getProductCode(),
                workOrderCreateRequest.getEmployeeCode(),
                workOrderCreateRequest.getOrderedQuantity(),
                workOrderCreateRequest.getCompletionStatus()
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

    public void workOrderModify(Long workOrderCode, WorkOrderUpdateRequest workOrderUpdateRequest) {
        // 작업 코드로 작업을 조회
        WorkOrder workOrder = workOrderRepo.findByWorkOrderCode(workOrderCode)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_WORK_ORDER));

        // 작업이 존재하는 경우에만 작업의 상태를 확인하여 수정 가능 여부를 결정
        if (workOrder.getCompletionStatus() == WorkOrderStatusType.IN_PROGRESS) {
            // 작업 수정 코드
            workOrder.workOrderModify(
                    workOrderUpdateRequest.getWorkOrderDate(),
                    workOrderUpdateRequest.getOrderedQuantity(),
                    workOrderUpdateRequest.getLineCode(),
                    workOrderUpdateRequest.getEmployeeCode()
            );
        } else if (workOrder.getCompletionStatus() == WorkOrderStatusType.DONE) {
            // 완료 상태인 경우 수정할 수 없음을 알림
            throw new NotFoundException(ExceptionCode.BAD_REQUEST_WORK_ORDER_DONE);
        } else {
            // 작업이 없는 경우 수정할 수 없음을 알림
            throw new NotFoundException(ExceptionCode.NOT_FOUND_WORK_ORDER);
        }
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
