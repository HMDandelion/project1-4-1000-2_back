package com.hmdandelion.project_1410002.sales.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.sales.dto.response.EstimatesResponse;
import com.hmdandelion.project_1410002.sales.service.EstimateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EstimateController {

    private final EstimateService estimateService;

    @GetMapping("/estimates")
    public ResponseEntity<PagingResponse> getEstimates(
            @RequestParam(defaultValue = "1") final Integer page,
            @RequestParam(required = false) final String sort,
            @RequestParam(required = false) final String clientName,
            @RequestParam(required = false) final String createdAt
    ) {
        Page<EstimatesResponse> estimates = estimateService.getEstimates(page, sort, clientName, createdAt);
        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(estimates);
        final PagingResponse pagingResponse = PagingResponse.of(estimates.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }
}
