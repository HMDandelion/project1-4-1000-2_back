package com.hmdandelion.project_1410002.production.service;

import com.hmdandelion.project_1410002.production.domain.entity.material.MaterialUsage;
import com.hmdandelion.project_1410002.production.domain.repository.material.MaterialUsageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MaterialUsageService {

    private final MaterialUsageRepo materialUsageRepo;

    public void usageCreate(Long workOrderCode, LocalDate workOrderDate) {
        MaterialUsage createOne = MaterialUsage.of(workOrderCode, workOrderDate);
        materialUsageRepo.save(createOne);
    }


}
