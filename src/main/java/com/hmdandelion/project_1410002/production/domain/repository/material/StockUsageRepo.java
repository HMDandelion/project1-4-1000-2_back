package com.hmdandelion.project_1410002.production.domain.repository.material;

import com.hmdandelion.project_1410002.production.domain.entity.material.StockUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockUsageRepo extends JpaRepository<StockUsage,Long> {

    List<StockUsage> findAllByUsageCode(Long usageCode);
}
