package com.hmdandelion.project_1410002.sales.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.sales.dto.request.EstimateCreateRequest;
import com.hmdandelion.project_1410002.sales.dto.request.EstimateUpdateRequest;
import com.hmdandelion.project_1410002.sales.dto.response.EstimateResponse;
import com.hmdandelion.project_1410002.sales.dto.response.EstimatesResponse;
import com.hmdandelion.project_1410002.sales.service.EstimateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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

    @GetMapping("/estimates/{estimateCode}")
    public ResponseEntity<EstimateResponse> getEstimate(@PathVariable final Long estimateCode) {
        final EstimateResponse estimateResponse = estimateService.getEstimate(estimateCode);
        return ResponseEntity.ok(estimateResponse);
    }

    @PostMapping("/estimates")
    public ResponseEntity<Void> save(@RequestBody EstimateCreateRequest estimateRequest) {
        final Long estimateCode = estimateService.save(estimateRequest);
        return ResponseEntity.created(URI.create("/api/v1/estimates/" + estimateCode)).build();
    }

    @PutMapping("/estimates/{estimateCode}")
    public ResponseEntity<Void>  modify(
            @PathVariable final Long estimateCode,
            @RequestBody EstimateUpdateRequest estimateRequest
    ) {
        estimateService.modify(estimateCode, estimateRequest);
        return ResponseEntity.created(URI.create("/api/v1/estimates/" + estimateCode)).build();
    }

    @DeleteMapping("/estimates/{estimateCode}")
    public ResponseEntity<Void> remove(@PathVariable final Long estimateCode) {
        estimateService.remove(estimateCode);
        return ResponseEntity.noContent().build();
    }

}
