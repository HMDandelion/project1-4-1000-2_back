package com.hmdandelion.project_1410002.production.presentation;

import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import com.hmdandelion.project_1410002.production.dto.request.DailyProductionRequest;
import com.hmdandelion.project_1410002.production.dto.response.ProductionReportResponse;
import com.hmdandelion.project_1410002.production.service.ProductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductionManagerController {

    private final ProductionService productionService;


    /*조회*/
    @GetMapping("/production/reports")
    public ResponseEntity<PagingResponse> getProductionReports(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Long productionStatusCode,
            @RequestParam(required = false) ProductionStatusType productionStatusType,
            @RequestParam(required = false) LocalDateTime startAt,
            @RequestParam(required = false) LocalDateTime completedAt) {

        if (page == null || page <= 0) {
            page = 1;
        }


        final Page<ProductionReportResponse> productionReportResponses = productionService.getProductionReportRecords(page, productionStatusCode, productionStatusType, startAt, completedAt);
        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(productionReportResponses);
        final PagingResponse pagingResponse = PagingResponse.of(productionReportResponses.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }

@PostMapping("/production/report")
    public ResponseEntity<Void> save(
            @RequestPart @RequestBody final DailyProductionRequest dailyProductionRequest) {

//    final Long productionStatusCode = productionService.save(dailyProductionRequest);


    return ResponseEntity.created(URI.create("/api/v1/production/")).build();
}
}
