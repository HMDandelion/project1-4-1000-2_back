package com.hmdandelion.project_1410002.production.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.production.dto.request.PlannedOrderListRequest;
import com.hmdandelion.project_1410002.production.dto.request.ProductionPlanCreateRequest;
import com.hmdandelion.project_1410002.production.dto.request.ProductionPlanUpdateRequest;
import com.hmdandelion.project_1410002.production.dto.response.PlanListResponse;
import com.hmdandelion.project_1410002.production.dto.response.production.SimplePlanResponse;
import com.hmdandelion.project_1410002.production.service.PlanService;
import lombok.Getter;
import com.hmdandelion.project_1410002.sales.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductionPlanController {

    private final PlanService planService;

    private final OrderService orderService;

    /* 내가 설정한 시작날짜 종료날짜에 대한 생산 계획 조회 start */
    @GetMapping("/production/planning")
    public ResponseEntity<PagingResponse> getPlanList (
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final String dt,
            @RequestParam(defaultValue = "1") final Integer page)
    {
        final Page<PlanListResponse> plan = planService.getPlanListBetweenDates(dt,page);

        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(plan);
        final PagingResponse pagingResponse = PagingResponse.of(plan.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }
    /* 내가 설정한 시작날짜 종료날짜에 대한 생산 계획 조회 end */

    /* 생산 계획 등록 start */
    @PostMapping("/production/planning")
    public ResponseEntity<Void> planSave (
            @RequestBody final ProductionPlanCreateRequest productionPlanCreateRequest)
      {
            final Long planCode = planService.planSave(productionPlanCreateRequest);

          List<PlannedOrderListRequest> plannedOrderListRequests = productionPlanCreateRequest.getPlannedOrderListRequests();
          for (PlannedOrderListRequest orderRequest : plannedOrderListRequests) {
              orderService.updateOrderStatusToInProduction(orderRequest.getOrderCode());
          }

            return ResponseEntity.created(URI.create("/api/v1/production/planning/" + planCode)).build();
    }
    /* 생산 계획 등록 end */

    /* 생산 계획 수정 start */
    @PutMapping("/production/planning/{planCode}")
    public ResponseEntity<Void> planModify(
            @PathVariable final Long planCode,
            @RequestBody final ProductionPlanUpdateRequest productionPlanUpdateRequest)
    {
        planService.planModify(planCode, productionPlanUpdateRequest);

        return ResponseEntity.created(URI.create("/api/v1/production/planning/" + planCode)).build();
    }
    /* 생산 계획 수정 end */

    /* 생산 계획 삭제 start */
    @DeleteMapping("/production/planning/{planCode}")
    public ResponseEntity<Void> planRemove(
            @PathVariable final Long planCode)
            {
                planService.planRemove(planCode);

                return ResponseEntity.noContent().build();
            }
    /* 생산 계획 삭제 end */


    // 한결 : 금일 이후의 생산계획을 조회하는 기능
    @GetMapping("/production/planning/today-after")
    public ResponseEntity<List<SimplePlanResponse>> getPlanTodayAfter() {
        final List<SimplePlanResponse> list = planService.getPlanTodayAfter();
        return ResponseEntity.of(Optional.ofNullable(list));
    }

}
