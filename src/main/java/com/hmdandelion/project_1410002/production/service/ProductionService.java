package com.hmdandelion.project_1410002.production.service;

import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.production.domain.entity.production.DefectDetail;
import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionManagement;
import com.hmdandelion.project_1410002.production.domain.repository.production.DefectDetailRepo;
import com.hmdandelion.project_1410002.production.domain.repository.production.ProductionRepo;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import com.hmdandelion.project_1410002.production.dto.response.production.DefectDetailResponse;
import com.hmdandelion.project_1410002.production.dto.response.production.ProductionDetailResponse;
import com.hmdandelion.project_1410002.production.dto.response.production.ProductionReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductionService {

    private final ProductionRepo productionRepo;
    private final DefectDetailRepo defectDetailRepo;

    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 20, Sort.by("productionStatusCode").descending());
    }

    @Transactional(readOnly = true)
    public Page<ProductionReportResponse> getProductionReportRecords(final Integer page, final Long productionStatusCode, final ProductionStatusType productionStatusType, final LocalDateTime startAt, final LocalDateTime completedAt) {

        Page<ProductionManagement> productionManagements = null;
        if (productionStatusCode != null && productionStatusCode > 0) {
            productionManagements = productionRepo.findByProductionStatusCodeAndProductionStatus(getPageable(page), productionStatusCode, ProductionStatusType.REGISTER_PRODUCTION);
        } else if (productionStatusType != null) {
            productionManagements = productionRepo.findByProductionStatus(getPageable(page), productionStatusType);
        } else if (completedAt != null && startAt != null) {
            productionManagements = productionRepo.findByCompletedAtBetween(getPageable(page), startAt, completedAt);
        } else if (completedAt != null && startAt == null) {
            productionManagements = productionRepo.findByCompletedAt(getPageable(page), completedAt);
        } else if (completedAt == null && startAt != null) {
            productionManagements = productionRepo.findByStartAt(getPageable(page), startAt);
        } else if (startAt == null && completedAt == null && productionStatusCode == null && productionStatusType == null) {
            productionManagements = productionRepo.findAll(getPageable(page));
        } else {
            productionManagements = productionRepo.findByProductionStatusNot(getPageable(page), ProductionStatusType.WAIT);
        }

        return productionManagements.map(ProductionReportResponse::from);
    }

    /* 상품 상세 조회 */
    @Transactional(readOnly = true)
    public List<ProductionDetailResponse> getProductionDetails(Long productionStatusCode) {
        Optional<ProductionManagement> optionalProductionManagement = productionRepo.findByProductionStatusCode(productionStatusCode);
        ProductionManagement productionManagement = optionalProductionManagement.orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCTION_CODE));

        return productionManagement.getProductionDetails().stream()
                .map(ProductionDetailResponse::from)
                .collect(Collectors.toList());
    }

    /* 불량 상세 조회 */
    @Transactional(readOnly = true)
    public List<DefectDetailResponse> getDefectDetails(Long defectCode) {
        List<DefectDetail> defectDetails = defectDetailRepo.findByDefectCode(defectCode);

        if (defectDetails.isEmpty()) {
            throw new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCTION_DETAIL);
        }

        return defectDetails.stream()
                .map(DefectDetailResponse::from)
                .collect(Collectors.toList());
    }
}