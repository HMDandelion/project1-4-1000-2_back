package com.hmdandelion.project_1410002.sales.domain.repository;

import com.hmdandelion.project_1410002.sales.domain.entity.estimate.EstimateProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstimateProductRepo extends JpaRepository<EstimateProduct, Long> {
}
