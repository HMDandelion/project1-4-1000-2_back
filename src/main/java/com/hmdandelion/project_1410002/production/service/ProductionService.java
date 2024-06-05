package com.hmdandelion.project_1410002.production.service;

import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.production.domain.entity.production.DefectDetail;
import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionDetail;
import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionManagement;
import com.hmdandelion.project_1410002.production.domain.repository.production.DefectDetailRepo;
import com.hmdandelion.project_1410002.production.domain.repository.production.ProductionDetailRepo;
import com.hmdandelion.project_1410002.production.domain.repository.production.ProductionRepo;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import com.hmdandelion.project_1410002.production.dto.request.ProductionReportCreateRequest;
import com.hmdandelion.project_1410002.production.dto.request.ProductionReportUpdateRequest;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductionService {

    private final ProductionRepo productionRepo;
    private final DefectDetailRepo defectDetailRepo;
    private final ProductionDetailRepo productionDetailRepo;

    /* 페이징 */
    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 20, Sort.by("productionStatusCode").descending());
    }

    /* 전체 조회*/
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

    /* 상세 조회 */
    /* 상세 조회 */
    @Transactional(readOnly = true)
    public List<ProductionDetailResponse> getProductionDetails(Long productionStatusCode) {
        Optional<ProductionManagement> optionalProductionManagement = productionRepo.findByProductionStatusCode(productionStatusCode);
        ProductionManagement productionManagement = optionalProductionManagement.orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCTION_CODE));

        List<ProductionDetailResponse> productionDetails = new ArrayList<>();
        for (ProductionDetail productionDetail : productionManagement.getProductionDetails()) {
            productionDetails.add(ProductionDetailResponse.from(productionDetail));
        }
        return productionDetails;
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

//    /* 보고서 등록 */
//    @Transactional
//    public Long reportSave(ProductionReportCreateRequest productionReportCreateRequest, ProductionStatusType registerProduction) {
////       ProductionDetail productionDetail = productionDetailRepo.getReferenceById(productionReportCreateRequest.getProductionDetailCode() );
////        DefectDetail defectDetail = defectDetailRepo.getReferenceById(productionReportCreateRequest.getDefectCode());
//        final ProductionManagement newProductionManagement = ProductionManagement.of(
//                productionReportCreateRequest.getStartAt(),
//                productionReportCreateRequest.getCompletedAt(),
//                productionReportCreateRequest.getTotalProductionQuantity(),
//                productionReportCreateRequest.getProductionFile(),
//                productionReportCreateRequest.getProductionStatus(),
//                productionReportCreateRequest.getInspectionStatus()
////                productionDetail,
////                defectDetail
//        );
//        productionRepo.save(newProductionManagement);
//
//        final ProductionDetail newProductionDetail = ProductionDetail.of(
//                newProductionManagement,
//                productionReportCreateRequest.getInspectionDate(),
//                productionReportCreateRequest.getProductionQuantity(),
//                productionReportCreateRequest.getDefectQuantity(),
//                productionReportCreateRequest.getCompletelyQuantity(),
//                productionReportCreateRequest.getProductionMemo(),
//                productionReportCreateRequest.getProductionStatus()
//        );
//        productionDetailRepo.save(newProductionDetail);
//
//        // 불량 상세 정보 생성 및 저장
//        final DefectDetail newDefectDetail = DefectDetail.of(
//                newProductionDetail,
//                productionReportCreateRequest.getDefectReason(),
//                productionReportCreateRequest.getDefectStatus(),
//                productionReportCreateRequest.getDefectFile()
//        );
//        defectDetailRepo.save(newDefectDetail);
//
//        return newProductionDetail.getProductionDetailCode();
//    }
//
//
//    @Transactional
//    public void modifyReport(Long productionStatusCode, ProductionReportUpdateRequest productionReportUpdateRequest) {
//        // ProductionManagement 엔터티 가져오기
//        ProductionManagement productionManagement = productionRepo.findByProductionStatusCode(productionStatusCode)
//                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCTION_CODE));
//
//        // ProductionManagement 엔터티 수정
//        productionManagement.modifyReport(
//                productionReportUpdateRequest.getStartAt(),
//                productionReportUpdateRequest.getCompletedAt(),
//                productionReportUpdateRequest.getTotalProductionQuantity(),
//                productionReportUpdateRequest.getProductionFile(),
//                productionReportUpdateRequest.getProductionStatus(),
//                productionReportUpdateRequest.getInspectionStatus()
//        );
//        // ProductionDetail 엔터티 수정
//        for (ProductionDetail productionDetail : productionManagement.getProductionDetails()) {
//            if (productionDetail.getProductionDetailCode().equals(productionReportUpdateRequest.getProductionDetailCode())) {
//                productionDetail.modifyDetail(
//                        productionReportUpdateRequest.getInspectionDate(),
//                        productionReportUpdateRequest.getProductionQuantity(),
//                        productionReportUpdateRequest.getDefectQuantity(),
//                        productionReportUpdateRequest.getCompletelyQuantity(),
//                        productionReportUpdateRequest.getProductionMemo(),
//                        productionReportUpdateRequest.getProductionStatus()
//                );
//
//                // 연관된 불량 상세 정보 수정
//                List<DefectDetail> defectDetails = defectDetailRepo.findByProductionDetail(productionDetail);
//                for (DefectDetail defectDetail : defectDetails) {
//                    defectDetail.modifyDetail(
//                            productionReportUpdateRequest.getDefectReason(),
//                            productionReportUpdateRequest.getDefectStatus(),
//                            productionReportUpdateRequest.getDefectFile()
//                    );
//                }
//            }
//        }
//// 저장된 엔터티를 업데이트
//        productionRepo.save(productionManagement);
//    }

    // 저장된 엔터티를 업데이트

    @Transactional
    public void removeReport(Long productionStatusCode) {
        // 보고서 삭제 전 보고서 찾는 로직
        Optional<ProductionManagement> optionalProductionManagement = productionRepo.findByProductionStatusCode(productionStatusCode);

        // 보고서가 존재하면 삭제.
        optionalProductionManagement.ifPresent(productionRepo::delete);
    }


    /* -------------------------- 계산기 --------------------------------------------------*/
    public int calculateProductionQuantity(int defectQuantity, int completelyQuantity) {
        return defectQuantity + completelyQuantity;
    }

    @Transactional(readOnly = true)
    public int calculateTotalProductionQuantity() {
        List<ProductionDetail> allProductionDetails = productionDetailRepo.findAll();

        int totalProductionQuantity = allProductionDetails.stream()
                .mapToInt(productionDetail -> calculateProductionQuantity(productionDetail.getDefectQuantity(), productionDetail.getCompletelyQuantity()))
                .sum();

        return totalProductionQuantity;
    }

}