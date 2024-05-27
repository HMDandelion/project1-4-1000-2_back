package com.hmdandelion.project_1410002.production.service;

import com.hmdandelion.project_1410002.production.domain.entity.ProductionPlan;
import com.hmdandelion.project_1410002.production.domain.repository.ProductionRepository;
import com.hmdandelion.project_1410002.production.dto.request.ProductionPlanCreateRequest;
import com.hmdandelion.project_1410002.production.dto.response.PlanListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlanService {

    private final ProductionRepository productionRepository;

    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 10, Sort.by("planCode").descending());
    }

    public Page<PlanListResponse> getPlanList(Integer page) {
        Page<ProductionPlan> products = productionRepository.findByStatusNot(getPageable(page));

        return null;// products.map(PlanListResponse::from)
    }

    public Long save(final ProductionPlanCreateRequest productionPlanCreateRequest) {
        final ProductionPlan
                newPlan = ProductionPlan.of(
                productionPlanCreateRequest.getStartAt(),
                productionPlanCreateRequest.getDescription(),
                productionPlanCreateRequest.getEndAt()

        );
        final ProductionPlan plan = productionRepository.save(newPlan);
        return plan.getPlanCode();
    }
}
