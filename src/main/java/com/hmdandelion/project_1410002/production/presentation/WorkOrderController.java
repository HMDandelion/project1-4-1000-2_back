package com.hmdandelion.project_1410002.production.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.production.domain.type.WorkOrderStatusType;
import com.hmdandelion.project_1410002.production.dto.request.WorkOrderCreateRequest;
import com.hmdandelion.project_1410002.production.dto.request.WorkOrderUpdateRequest;
import com.hmdandelion.project_1410002.production.dto.response.WorkOrderResponse;
import com.hmdandelion.project_1410002.production.service.WorkOrderService;
import com.hmdandelion.project_1410002.sales.dto.response.EstimateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    /* 작업 지시서 전체 목록 조회 start */
    @GetMapping("/production/work-order")
    public ResponseEntity<PagingResponse> getWorkOrders (
            @RequestParam(defaultValue = "1") final Integer page,
            @RequestParam(defaultValue = "10") final int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        final Page<WorkOrderResponse> workOrders = workOrderService.getWorkOrders(page);
        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(workOrders);
        final PagingResponse pagingResponse = PagingResponse.of(workOrders.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }
    /* 작업 지시서 전체 목록 조회 end */

    /* 작업 지시서 상세 조회 start */
    @GetMapping("/production/work-order/{workOrderCode}")
    public ResponseEntity<WorkOrderResponse> workOrderFindCode(@PathVariable Long workOrderCode){
        final WorkOrderResponse workOrderResponse = workOrderService.getWorkOrder(workOrderCode);
        return ResponseEntity.ok(workOrderResponse);
    }
    /* 작업 지시서 상세 조회 end */

    /* 작업 지시서 등록내에 조회 start */
//    @GetMapping("/production/work-order/")
//    public ResponseEntity<WorkOrderResponse> getWorkOrderInSave (
//    ) {
//        final WorkOrderResponse WorkOrderResponse = workOrderService.getWorkOrderInSave();
//
//        return ResponseEntity.ok(WorkOrderResponse);
//    }
    /* 작업 지시서 등록내에 조회 end */


    /* 작업 지시서 등록 start */
    @PostMapping("/production/work-order")
    public ResponseEntity<Void> workOrderSave (
            @RequestBody final WorkOrderCreateRequest workOrderCreateRequest)
    {

        final Long workOrderCode = workOrderService.workOrderSave(workOrderCreateRequest);

        return ResponseEntity.created(URI.create("/api/v1/production/work-order/" + workOrderCode)).build();
    }
    /* 작업 지시서 등록 end */

    /* 작업 지시서 수정 start */
    @PutMapping("/production/work-order/{workOrderCode}")
    public ResponseEntity<Void> workOrderModify (
            @PathVariable final Long workOrderCode,
            @RequestBody final WorkOrderUpdateRequest workOrderUpdateRequest)
    {
        workOrderService.workOrderModify(workOrderCode, workOrderUpdateRequest);

        return ResponseEntity.created(URI.create("/api/v1/production/work-order/" + workOrderCode)).build();
    }
    /* 작업 지시서 수정 end */

    /* 작업 지시서 삭제 start */
    @DeleteMapping("/production/work-order/{workOrderCode}")
    public ResponseEntity<Void> workOrderRemove (
            @PathVariable final Long workOrderCode)
    {
        workOrderService.workOrderRemove(workOrderCode);

        return ResponseEntity.noContent().build();
    }
    /* 작업 지시서 삭제 end */

    /* 작업 지시서 상태 변경 start */
    @PostMapping("/production/{workOrderCode}/workOrderEnd")
    public ResponseEntity<String> endOver (
            @PathVariable Long workOrderCode
    ){
        workOrderService.end(workOrderCode);
        return ResponseEntity.ok(
                "상태 변경이 완료 되었습니다."
        );
    }
    /* 작업 지시서 상태 변경 end */

}
