package com.hmdandelion.project_1410002.production.service;

import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.production.domain.entity.PlannedOrderList;
import com.hmdandelion.project_1410002.production.domain.entity.ProductionPlan;
import com.hmdandelion.project_1410002.production.domain.entity.ProductionPlannedList;
import com.hmdandelion.project_1410002.production.domain.repository.productionPlan.ProductionPlanRepo;
import com.hmdandelion.project_1410002.production.dto.request.ProductionPlanCreateRequest;
import com.hmdandelion.project_1410002.production.dto.request.ProductionPlanUpdateRequest;
import com.hmdandelion.project_1410002.production.dto.response.PlanListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static com.hmdandelion.project_1410002.common.exception.type.ExceptionCode.ALREADY_EXIST_PRODUCTION_PLAN;


@Service
@RequiredArgsConstructor
@Transactional
public class PlanService {

    private final ProductionPlanRepo productionPlanRepo;

    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 10, Sort.by("planCode").descending());
    }

    @Transactional(readOnly = true)
    public Page<PlanListResponse> getPlanListBetweenDates(String dt, Integer page) {

        LocalDate startAt = LocalDate.parse(dt + "-01");
        LocalDate endAt = startAt.with(TemporalAdjusters.lastDayOfMonth());

        Page<PlanListResponse> planList = productionPlanRepo.findPlanDetails(getPageable(page), startAt , endAt);

        return planList;
    }

    public Long planSave(final ProductionPlanCreateRequest productionPlanCreateRequest) {

        // 새로운 생산 계획의 시작 날짜와 종료 날짜
        LocalDate startAt = productionPlanCreateRequest.getStartAt();
        LocalDate endAt = productionPlanCreateRequest.getEndAt();

        // 해당 날짜와 겹치는 계획이 있는지 확인
        boolean overlappingPlansExist = productionPlanRepo.existsByDateRange(startAt, endAt);
        if (overlappingPlansExist) {
            // 겹치는 계획이 있으면 예외를 발생시킵니다.
            throw new NotFoundException(ALREADY_EXIST_PRODUCTION_PLAN);
        }

        List<ProductionPlannedList> productionPlanList = productionPlanCreateRequest.getProductionPlannedLists().stream()
                                                                                    .map(productionPlannedListRequest -> {
                    return ProductionPlannedList.of(
                            productionPlannedListRequest.getProductCode(),
                            productionPlannedListRequest.getPlannedQuantity(),
                            productionPlannedListRequest.getDescription(),
                            productionPlannedListRequest.getRequiredQuantity()
//                            ,newPlan

                    );
                }).toList();

        List<PlannedOrderList> plannedOrderList = productionPlanCreateRequest.getPlannedOrderListRequests().stream()
                .map(plannedOrderListRequest -> {
                    return PlannedOrderList.of(
                            plannedOrderListRequest.getOrderCode()
//                            ,
//                            newPlan

                    );
                }).toList();

        final ProductionPlan newPlan = ProductionPlan.of(
                productionPlanCreateRequest.getStartAt(),
                productionPlanCreateRequest.getEndAt(),
                productionPlanList,
                plannedOrderList
        );

//        newPlan.createPlan(productionPlanList, plannedOrderList);

        final ProductionPlan plan = productionPlanRepo.save(newPlan);

        return plan.getPlanCode();
    }


    public void planModify(Long planCode, ProductionPlanUpdateRequest productionPlanUpdateRequest) {
        ProductionPlan productionPlan = productionPlanRepo.findByPlanCode(planCode)
                                                          .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PLAN_CODE));

        productionPlanUpdateRequest.getProductionPlannedLists().forEach(
                productionPlannedListRequest -> {
                    ProductionPlannedList productionPlannedList = productionPlan.getProductionPlannedList().stream()
                           .filter(productionPlannedList1 -> productionPlannedList1.getPlanListCode().equals(productionPlannedListRequest.getPlanListCode()))
                           .findFirst()
                            .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCTION_PLANNED_LIST_CODE));

                    productionPlannedList.planModify(
                            productionPlannedListRequest.getPlannedQuantity(),
                            productionPlannedListRequest.getDescription()
                    );
                }
        );
        productionPlan.planModify(
                productionPlanUpdateRequest.getStartAt(),
                productionPlanUpdateRequest.getEndAt()
        );
    }

    public void planRemove(Long planCode) {

        productionPlanRepo.deleteById(planCode);
    }

    //플랜코드로 플랜을 조회하는 코드 (by한결)
    public ProductionPlan findById(Long planCode) {
        return productionPlanRepo.findById(planCode).orElseThrow(()-> new NotFoundException(ExceptionCode.NOT_FOUND_PLAN_CODE));
    }
}
