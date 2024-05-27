package com.hmdandelion.project_1410002.production.presentation;

import com.hmdandelion.project_1410002.production.common.paging.Pagination;
import com.hmdandelion.project_1410002.production.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.production.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.production.dto.response.PlanListResponse;
import com.hmdandelion.project_1410002.production.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/production")
@RequiredArgsConstructor
public class planController {
    private final PlanService planService;

    /* 생산 계획 전체 조회 */
    @GetMapping("/work-order")
    public ResponseEntity<PagingResponse> getPlanList(@RequestParam(defaultValue = "1") final Integer page){
        final Page<PlanListResponse
                > plan = planService.getPlanList(page);
        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(plan);
        final PagingResponse pagingResponse = PagingResponse.of(plan.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }

    @PostMapping("/planning")
    public ResponseEntity<Void> planSave(){
            return null;
    }

}
