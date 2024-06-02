package com.hmdandelion.project_1410002.production.presentation;

import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.paging.Pagination;
import com.hmdandelion.project_1410002.common.paging.PagingButtonInfo;
import com.hmdandelion.project_1410002.common.paging.PagingResponse;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import com.hmdandelion.project_1410002.production.dto.request.ProductionReportCreateRequest;
import com.hmdandelion.project_1410002.production.dto.response.production.DefectDetailResponse;
import com.hmdandelion.project_1410002.production.dto.response.production.ProductionDetailResponse;
import com.hmdandelion.project_1410002.production.dto.response.production.ProductionReportResponse;
import com.hmdandelion.project_1410002.production.service.ProductionService;
import lombok.RequiredArgsConstructor;
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

    /* 상세 목록 조회 */
    @GetMapping("/production/reports/{productionDetailCode}")
    public ResponseEntity<List<ProductionDetailResponse>> getProductionDetails(
            @PathVariable Long productionDetailCode) {
        List<ProductionDetailResponse> productionDetails = productionService.getProductionDetails(productionDetailCode);
        return ResponseEntity.ok(productionDetails);
    }


    /* 불량상세 조회 */
    @GetMapping("/production/reports/{productionDetailCode}/defects")
    public ResponseEntity<List<DefectDetailResponse>> getDefectDetails(
            @PathVariable Long productionDetailCode) {
        try {
            List<DefectDetailResponse> defectDetails = productionService.getDefectDetails(productionDetailCode);
            return ResponseEntity.ok(defectDetails);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

    }
     /*보고서 등록*/
    @PostMapping("/production/reports")
    public ResponseEntity<Void> reportSaved(
            @RequestPart final ProductionReportCreateRequest productionReportCreateRequest,
            @RequestPart final MultipartFile attachFile) {
        final Long productionDetailCode = productionService.reportSave(productionReportCreateRequest, attachFile, ProductionStatusType.REGISTER_PRODUCTION);

        return ResponseEntity.created(URI.create("/api/v1/production/reports/" + productionDetailCode)).build();

    }
}