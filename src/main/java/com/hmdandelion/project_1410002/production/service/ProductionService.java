package com.hmdandelion.project_1410002.production.service;

import com.hmdandelion.project_1410002.common.exception.CustomException;
import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.employee.service.EmployeeService;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Stock;
import com.hmdandelion.project_1410002.inventory.domian.repository.product.ProductRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.stock.StockRepo;
import com.hmdandelion.project_1410002.inventory.service.ProductService;
import com.hmdandelion.project_1410002.production.domain.entity.WorkOrder;
import com.hmdandelion.project_1410002.production.domain.entity.production.DefectDetail;
import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionDetail;
import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionManagement;
import com.hmdandelion.project_1410002.production.domain.repository.production.DefectDetailRepo;
import com.hmdandelion.project_1410002.production.domain.repository.production.ProductionDetailRepo;
import com.hmdandelion.project_1410002.production.domain.repository.production.ProductionRepo;
import com.hmdandelion.project_1410002.production.domain.repository.productionPlan.WorkOrderRepo;
import com.hmdandelion.project_1410002.production.domain.type.ProductionStatusType;
import com.hmdandelion.project_1410002.production.domain.type.WorkOrderStatusType;
import com.hmdandelion.project_1410002.production.dto.request.createProductionRequest.DefectDetailCreateRequest;
import com.hmdandelion.project_1410002.production.dto.request.createProductionRequest.ProductionDetailCreateRequest;
import com.hmdandelion.project_1410002.production.dto.request.createProductionRequest.ReportCreateRequest;
import com.hmdandelion.project_1410002.production.dto.request.updateProductionRequest.DefectDetailUpdateRequest;
import com.hmdandelion.project_1410002.production.dto.request.updateProductionRequest.ProductionDetailUpdateRequest;
import com.hmdandelion.project_1410002.production.dto.request.updateProductionRequest.ReportUpdateRequest;
import com.hmdandelion.project_1410002.production.dto.response.production.DefectDetailResponse;
import com.hmdandelion.project_1410002.production.dto.response.production.ProductionDetailResponse;
import com.hmdandelion.project_1410002.production.dto.response.production.ProductionReportResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.hmdandelion.project_1410002.inventory.domian.type.StockType.RE_INSPECTION;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductionService {

    private final ProductionRepo productionRepo;
    private final ProductionDetailRepo productionDetailRepo;
    private final DefectDetailRepo defectDetailRepo;
    private final WorkOrderRepo workOrderRepo;
    private final StockRepo stockRepo;
    private final ProductRepo productRepo;
    private final LineService lineService;
    private final EmployeeService employeeService; // 수정된 부분
    private final ProductService productService; // 수정된 부분

    /* 페이징 */
    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 20, Sort.by("productionStatusCode").descending());
    }

    /* 전체 조회*/
    @Transactional(readOnly = true)
    public Page<ProductionReportResponse> getProductionReportRecords(final Pageable page, final Long productionStatusCode, final ProductionStatusType productionStatusType, final LocalDateTime startAt, final LocalDateTime completedAt) {
        Page<ProductionManagement> productionManagements = null;

        if (productionStatusCode != null && productionStatusCode > 0) {
//            log.info("findByProductionStatusCodeAndProductionStatus 실행됨");
            productionManagements = productionRepo.findByProductionStatusCodeAndProductionStatus(page, productionStatusCode, ProductionStatusType.REGISTER_PRODUCTION);
        } else if (productionStatusType != null) {
//            log.info("findByProductionStatus 실행됨");
            productionManagements = productionRepo.findByProductionStatus(page, productionStatusType);
        } else {
            productionManagements = productionRepo.findAll(page);
//            log.info("findAll로 실행됨");
        }
        return productionManagements.map(productionManagement -> {
            List<String> productNames = productionManagement.getProductionDetails().stream()
                    .map(detail -> productService.getProduct(detail.getWorkOrder().getProductCode()).getProductName())
                    .distinct()
                    .collect(Collectors.toList());

            String stylizationName = formatProductNames(productNames);
            Map<Long, Integer> orderedQuantityMap = productionManagement.getProductionDetails().stream()
                    .collect(Collectors.groupingBy(
                            detail -> detail.getWorkOrder().getWorkOrderCode(),
                            Collectors.summingInt(detail -> detail.getWorkOrder().getOrderedQuantity())
                    ));

// 각 그룹의 주문 수량을 합산하여 총 주문 수량을 계산합니다.
            int totalOrderedQuantity = orderedQuantityMap.values().stream()
                    .mapToInt(Integer::intValue)
                    .sum();

            return ProductionReportResponse.of(
                    productionManagement.getProductionStatusCode(),
                    productionManagement.getStartAt(),
                    productionManagement.getCompletedAt(),
                    stylizationName,
                    totalOrderedQuantity,
                    productionManagement.getTotalProductionQuantity(),
                    productionManagement.getProductionFile(),
                    productionManagement.getProductionStatus()
            );
        });
    }
    private String formatProductNames(List<String> productNames) {
        if (productNames == null || productNames.isEmpty()) {
            return "";
        }

        if (productNames.size() == 1) {
            return productNames.get(0);
        }

        return productNames.get(0) + " 외 " + (productNames.size() - 1) + "개";
    }




    /* 상세 조회 */
    @Transactional(readOnly = true)
    public List<ProductionDetailResponse> getProductionDetails(Long productionStatusCode) {
        Optional<ProductionManagement> optionalProductionManagement = productionRepo.findByProductionStatusCode(productionStatusCode);
        ProductionManagement productionManagement = optionalProductionManagement.orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCTION_CODE));

        List<ProductionDetailResponse> productionDetails = new ArrayList<>();
        for (ProductionDetail productionDetail : productionManagement.getProductionDetails()) {
            final String lineName = lineService.findNameByCode(productionDetail.getWorkOrder().getLineCode());
            final String employeeName = employeeService.findById(productionDetail.getWorkOrder().getEmployeeCode()).getEmployeeName();
            final String productName = productService.getProduct(productionDetail.getWorkOrder().getProductCode()).getProductName();

            final ProductionDetailResponse productionDetailResponse = ProductionDetailResponse.of(
                    productionDetail.getProductionDetailCode(),
                    productionDetail.getWorkOrder().getWorkOrderCode(),
                    lineName,
                    employeeName,
                    productName,
                    productionDetail.getWorkOrder().getOrderedQuantity(),
                    productionDetail.getProductionQuantity(),
                    productionDetail.getDefectQuantity(),
                    productionDetail.getCompletelyQuantity(),
                    productionDetail.getInspectionDate(),
                    productionDetail.getInspectionStatus(),
                    productionDetail.getProductionMemo(),
                    productionDetail.getProductionStatus()
            );
            productionDetails.add(productionDetailResponse);
            //            productionDetails.add(ProductionDetailResponse.from(productionDetail));
        }
        return productionDetails;
    }

    /* 불량 상세 조회 */
    @Transactional(readOnly = true)
    public List<DefectDetailResponse> getDefectDetails(Long productionDetailCode) {
        Optional<ProductionDetail> optionalProductionDetail = productionDetailRepo.findByProductionDetailCode(productionDetailCode);
        ProductionDetail productionDetail = optionalProductionDetail.orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_DEFECT_DATA));

        List<DefectDetailResponse> defectDetails = new ArrayList<>();
        for (DefectDetail defectDetail : productionDetail.getDefectDetails()) {
            defectDetails.add(DefectDetailResponse.from(defectDetail));
        }
        return defectDetails;
    }

    /* 보고서 등록 */
    public Long reportSave(ReportCreateRequest reportCreateRequest) {
        // ProductionManagement 생성 및 저장
        final ProductionManagement newProductionManagement = ProductionManagement.
                of(
                        reportCreateRequest.getStartAt(),
                        reportCreateRequest.getCompletedAt(),
                        reportCreateRequest.getTotalProductionQuantity(),
                        reportCreateRequest.getProductionFile(),
                        reportCreateRequest.getProductionStatus());
        productionRepo.save(newProductionManagement);

        // ProductionDetail 생성 및 저장
        for (ProductionDetailCreateRequest productionDetailRequest : reportCreateRequest.getProductionDetails()) {
            WorkOrder workOrder = workOrderRepo.findByWorkOrderCode(productionDetailRequest.getWorkOrderCode()).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_WORK_ORDER));
            ProductionDetail newProductionDetail = ProductionDetail.of(newProductionManagement, workOrder, productionDetailRequest.getProductionQuantity(), productionDetailRequest.getDefectQuantity(), productionDetailRequest.getCompletelyQuantity(), productionDetailRequest.getInspectionDate(), productionDetailRequest.getInspectionStatusType(), productionDetailRequest.getProductionMemo(), productionDetailRequest.getProductionStatusType()
            );
            productionDetailRepo.save(newProductionDetail);

            // 해당 ProductionDetail 에 대한 DefectDetail 생성 및 저장
            for (DefectDetailCreateRequest defectDetailRequest : productionDetailRequest.getDefectDetails()) {
                DefectDetail newDefectDetail = DefectDetail.of(newProductionDetail, defectDetailRequest.getDefectReason(), defectDetailRequest.getDefectStatus(), defectDetailRequest.getDefectFile());
                defectDetailRepo.save(newDefectDetail);
            }
            // ProductionDetail의 상태가 PRODUCTION_COMPLETED이면 WorkOrder의 completionStatus를 DONE으로 변경
            if (productionDetailRequest.getProductionStatusType() == ProductionStatusType.PRODUCTION_COMPLETED) {
                workOrder.setCompletionStatus(WorkOrderStatusType.DONE);
                workOrderRepo.save(workOrder);
            }
            // ProductionDetail의 상태가 PRODUCTION_COMPLETED이면 Stock에 저장
            if (productionDetailRequest.getProductionStatusType() == ProductionStatusType.PRODUCTION_COMPLETED) {
                Product product = productRepo.findById(workOrder.getProductCode())
                        .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
                Stock newStock = Stock.of(
                        Long.valueOf(newProductionDetail.getCompletelyQuantity()),
                        RE_INSPECTION,
                        product
                );
                stockRepo.save(newStock);
            }
        }
        return newProductionManagement.getProductionStatusCode();
    }

    /* 보고서 수정 */
    public void modifyReport(Long productionStatusCode, ReportUpdateRequest reportUpdateRequest) {
        ProductionManagement productionManagement = productionRepo.findByProductionStatusCode(productionStatusCode).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCTION_CODE));

        // ProductionManagement 엔터티 수정
        productionManagement.modifyReport(reportUpdateRequest.getProductionManagementUpdateRequest().getStartAt(), reportUpdateRequest.getProductionManagementUpdateRequest().getCompletedAt(), reportUpdateRequest.getProductionManagementUpdateRequest().getTotalProductionQuantity(), reportUpdateRequest.getProductionManagementUpdateRequest().getProductionFile(), reportUpdateRequest.getProductionManagementUpdateRequest().getProductionStatus());

        // ProductionDetail 엔터티 수정

        for (ProductionDetailUpdateRequest productionDetailRequest : reportUpdateRequest.getProductionDetailUpdateRequest()) {
            WorkOrder workOrder = workOrderRepo.findByWorkOrderCode(productionDetailRequest.getWorkOrderCode()).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_WORK_ORDER));

            ProductionDetail productionDetail = productionDetailRepo.findByProductionDetailCode(productionDetailRequest.getProductionStatusCode()).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCTION_DETAIL));
            productionDetail.modifyDetail(
                    productionDetailRequest.getProductionQuantity(), productionDetailRequest.
                            getDefectQuantity(), productionDetailRequest.getCompletelyQuantity(),
                    productionDetailRequest.getInspectionDate(), productionDetailRequest.getInspectionStatusType(),
                    productionDetailRequest.getProductionMemo(), productionDetailRequest.getProductionStatusType());

            // 연관된 불량 상세 정보 수정
            /* 타겟 엔티티 조회 */
            List<DefectDetail> defectDetails = defectDetailRepo.findByProductionDetail(productionDetail);
            Map<Long, DefectDetail> defectDetailMap = defectDetails.stream().collect(Collectors.toMap(DefectDetail::getDefectCode, Function.identity()));

            for (DefectDetailUpdateRequest defectDetailUpdateRequest : productionDetailRequest.getDefectDetailUpdateRequest()) {
                if (defectDetailUpdateRequest.getId() != null) {
                    DefectDetail defectDetailUpdate = defectDetailMap.get(defectDetailUpdateRequest.getId());
                    if (defectDetailUpdate != null) {
                        defectDetailUpdate.modifyDetail(defectDetailUpdate.getDefectReason(), defectDetailUpdate.getDefectStatus(), defectDetailUpdate.getDefectFile());
                        defectDetailMap.remove(defectDetailUpdateRequest.getId());
                    } else {
                        throw new NotFoundException(ExceptionCode.NOT_FOUND_DEFECT_DATA);
                    }
                } else {
                    DefectDetail newDefectDetail = new DefectDetail(productionDetail, defectDetailUpdateRequest.getDefectReason(), defectDetailUpdateRequest.getDefectStatus(), defectDetailUpdateRequest.getDefectFile());
                    defectDetailRepo.save(newDefectDetail);
                }
                /* 나윤님 작업지시서 상태 자동 변화 */
                if (productionDetailRequest.getProductionStatusType() == ProductionStatusType.PRODUCTION_COMPLETED) {
                    workOrder.setCompletionStatus(WorkOrderStatusType.DONE);
                    workOrderRepo.save(workOrder);
                }
                // ProductionDetail의 상태가 PRODUCTION_COMPLETED이면 Stock에 저장
                if (productionDetailRequest.getProductionStatusType() == ProductionStatusType.PRODUCTION_COMPLETED) {
                    Product product = productRepo.findById(workOrder.getProductCode())
                            .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
                    Stock newStock = Stock.of(
                            Long.valueOf(productionDetail.getCompletelyQuantity()),
                            RE_INSPECTION,
                            product
                    );
                    stockRepo.save(newStock);
                }
            }
            for (DefectDetail defectDetailToDelete : defectDetailMap.values()) {
                defectDetailRepo.delete(defectDetailToDelete);
            }
        }
        productionRepo.save(productionManagement);
    }

    /* 보고서 삭제 */
    public void removeReport(Long productionStatusCode) {
        // 보고서 찾는 로직
        Optional<ProductionManagement> optionalProductionManagement = productionRepo.findByProductionStatusCode(productionStatusCode);
        // 보고서 삭제
        optionalProductionManagement.ifPresent(productionRepo::delete);
    }
}
//    /* -------------------------- 계산기 --------------------------------------------------*/
//    public int calculateProductionQuantity(int defectQuantity, int completelyQuantity) {
//        return defectQuantity + completelyQuantity;
//    }
//
//    @Transactional(readOnly = true)
//    public int calculateTotalProductionQuantity() {
//        List<ProductionDetail> allProductionDetails = productionDetailRepo.findAll();
//
//        int totalProductionQuantity = allProductionDetails.stream()
//                .mapToInt(productionDetail -> calculateProductionQuantity(productionDetail.getDefectQuantity(), productionDetail.getCompletelyQuantity()))
//                .sum();
//
//        return totalProductionQuantity;
//    }

//   } else if (completedAt != null && startAt != null) {
//            productionManagements = productionRepo.findByCompletedAtBetween(getPageable(page), startAt, completedAt);
//        } else if (completedAt != null) {
//            productionManagements = productionRepo.findByCompletedAt(getPageable(page), completedAt);
//        } else if (startAt != null) {
//            productionManagements = productionRepo.findByStartAt(getPageable(page), startAt);