package com.hmdandelion.project_1410002.production.service;

import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionManagement;
import com.hmdandelion.project_1410002.production.domain.repository.production.ProductionRepo;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import com.hmdandelion.project_1410002.production.dto.response.ProductionReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductionService {

    private final ProductionRepo productionRepo;

    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 20, Sort.by("productionStatusCode").descending());
    }

    @Transactional(readOnly = true)
    public Page<ProductionReportResponse> getProductionReportRecords(final Integer page, final Long productionStatusCode, final ProductionStatusType productionStatusType,final LocalDateTime startAt, final LocalDateTime completedAt) {

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


}




//
//    public Long save(final DailyProductionRequest dailyProductionRequest) {
//        final ProductionManagement newProductionManagement = ProductionManagement.of(
//                dailyProductionRequest.getStartAt(),
//                dailyProductionRequest.getCompletedAt(),
//                dailyProductionRequest.getTotalProductionQuantity(),
//                dailyProductionRequest.getProductionFile(),
//                dailyProductionRequest.getProductionStatus(),
//                dailyProductionRequest.getInspectionStatus(),
//                dailyProductionRequest.getInspectionDate(),
//                dailyProductionRequest.getProductionQuantity(),
//                dailyProductionRequest.getDefectQuantity(),
//                dailyProductionRequest.getCompletelyQuantity(),
//                dailyProductionRequest.getProductionMemo(),
//                dailyProductionRequest.getDefectReason(),
//                dailyProductionRequest.getDefectStatus(),
//                dailyProductionRequest.getDefectFile()
//
//
//        );
//
//        final ProductionManagement productionManagement = productionRepository.save(newProductionManagement);
//
//        return productionManagement.getProductionStatusCode();
//
//
//
//    }
//
