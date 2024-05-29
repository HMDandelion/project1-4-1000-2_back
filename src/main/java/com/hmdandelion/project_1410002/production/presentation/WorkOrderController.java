package com.hmdandelion.project_1410002.production.presentation;

import com.hmdandelion.project_1410002.production.dto.request.ProductionPlanCreateRequest;
import com.hmdandelion.project_1410002.production.dto.request.WorkOrderCreateRequest;
import com.hmdandelion.project_1410002.production.service.PlanService;
import com.hmdandelion.project_1410002.production.service.WorkOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    /* 작업 지시서 전체 목록 조회 start */
    /* 작업 지시서 전체 목록 조회 end */

    /* 작업 지시서 상세 조회 start */
    /* 작업 지시서 상세 조회 end */

    /* 작업 지시서 등록 start */
    @PostMapping("/production/work-order")
    public ResponseEntity<Void> workOrderSave(
            @RequestBody final WorkOrderCreateRequest workOrderCreateRequest)
    {

        final Long workOrderCode = workOrderService.workOrderSave(workOrderCreateRequest);

        return ResponseEntity.created(URI.create("/api/v1/production/planning/" + workOrderCode)).build();
    }
    /* 작업 지시서 등록 end */

    /* 작업 지시서 수정 start */
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

}
