package com.hmdandelion.project_1410002.production.service;

import com.hmdandelion.project_1410002.production.domain.entity.ProductionPlannedList;
import com.hmdandelion.project_1410002.production.domain.repository.ProductionPlannedListRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductionPlannedListService {
    private final ProductionPlannedListRepo plannedOrderListRepo;

    public List<ProductionPlannedList> findByPlanCode(Long planCode) {
        return plannedOrderListRepo.findAllByPlanCode(planCode);
    }
}
