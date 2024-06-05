package com.hmdandelion.project_1410002.production.presentation;

import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import com.hmdandelion.project_1410002.production.dto.request.ProductionReportCreateRequest;
import com.hmdandelion.project_1410002.production.dto.request.ProductionReportUpdateRequest;
import com.hmdandelion.project_1410002.production.dto.response.production.DefectDetailResponse;
import com.hmdandelion.project_1410002.production.dto.response.production.ProductionDetailResponse;
import com.hmdandelion.project_1410002.production.dto.response.production.ProductionReportResponse;
import com.hmdandelion.project_1410002.production.service.ProductionService;
import com.hmdandelion.project_1410002.sales.dto.request.ClientUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.DomainEvents;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductionManagerController {

    private final ProductionService productionService;


    /*조회*/
    @GetMapping("/production/reports")
    public ResponseEntity<PagingResponse> getProductionReports(@RequestParam(required = false) Integer page, @RequestParam(required = false) Long productionStatusCode, @RequestParam(required = false) ProductionStatusType productionStatusType, @RequestParam(required = false) LocalDateTime startAt, @RequestParam(required = false) LocalDateTime completedAt) {

        if (page == null || page <= 0) {
            page = 1;
        }


        final Page<ProductionReportResponse> productionReportResponses = productionService.getProductionReportRecords(page, productionStatusCode, productionStatusType, startAt, completedAt);
        final PagingButtonInfo pagingButtonInfo = Pagination.getPagingButtonInfo(productionReportResponses);
        final PagingResponse pagingResponse = PagingResponse.of(productionReportResponses.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }

    /* 상세 목록 조회 */
    @GetMapping("/production/reports/{productionStatusCode}/detail")
    public ResponseEntity<List<ProductionDetailResponse>> getProductionDetails(@PathVariable Long productionStatusCode) {
        List<ProductionDetailResponse> productionDetails = productionService.getProductionDetails(productionStatusCode);
        return ResponseEntity.ok(productionDetails);
    }

    /*동환 : 생산 완료로 상태 변화*/
    @PutMapping("/production")


    /* 불량상세 조회 */
    @GetMapping("/production/reports/{productionDetailCode}/defects")
    public ResponseEntity<List<DefectDetailResponse>> getDefectDetails(@PathVariable Long productionDetailCode) {
        try {
            List<DefectDetailResponse> defectDetails = productionService.getDefectDetails(productionDetailCode);
            return ResponseEntity.ok(defectDetails);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

    }

    /*보고서 등록*/
    @PostMapping("/production/reports")
    public ResponseEntity<Void> reportSaved(@RequestBody final ProductionReportCreateRequest productionReportCreateRequest
            //  @RequestPart final MultipartFile attachFile
    ) {
        final Long productionStatusCode = productionService.reportSave(productionReportCreateRequest, ProductionStatusType.REGISTER_PRODUCTION);
        // 총 생산량 계산
        int totalProductionQuantity = productionService.calculateTotalProductionQuantity();
        return ResponseEntity.created(URI.create("/api/v1/production/reports/" + productionStatusCode)).build();

    }

    /* 보고서 수정 */
    @PutMapping("production/reports/{productionStatusCode}/modify")
    public ResponseEntity<Void> modify(@PathVariable final Long productionStatusCode, @RequestBody final ProductionReportUpdateRequest productionReportUpdateRequest) {
        productionService.modifyReport(productionStatusCode, productionReportUpdateRequest);

        return ResponseEntity.created(URI.create("/api/v1/production/reports/" + productionStatusCode)).build();

    }

    @DeleteMapping("production/reports/{productionStatusCode}/delete")
    public ResponseEntity<Void> response(@PathVariable final Long productionStatusCode) {
        productionService.removeReport(productionStatusCode);
        return ResponseEntity.noContent().build();
    }
}